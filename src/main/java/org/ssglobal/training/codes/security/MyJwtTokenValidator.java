package org.ssglobal.training.codes.security;

import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Base64.Decoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.ssglobal.training.codes.repositories.UserTokenRepository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class MyJwtTokenValidator extends OncePerRequestFilter {

	@Autowired
	private final UserTokenRepository userTokenRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
			String token = authorizationHeader.substring("Bearer".length()).trim();
			if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer")) {
				response.sendError(HttpStatus.UNAUTHORIZED.value(), "not ok");
			}
			if (!validateToken(token)) {
				response.sendError(HttpStatus.UNAUTHORIZED.value(), "not ok");
			}
			
			filterChain.doFilter(request, response);
		} catch (NullPointerException e) {
			response.sendError(HttpStatus.UNAUTHORIZED.value(), "not ok");
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return request.getRequestURI().equals("/api/users/authenticate") || 
			   request.getRequestURI().equals("/api/users/insert") ||
			   request.getRequestURI().equals("/api/interests/get") ||
			   request.getRequestURI().contains("/api/images/") ||
			   request.getRequestURI().equals("/api/users/update/password");
	}

	@SuppressWarnings("unchecked")
	private boolean validateToken(String token) {
		try {
			String[] chunks = token.split("\\.");
			Decoder decoder = Base64.getUrlDecoder();
			String payload = new String(decoder.decode(chunks[1]));
			HashMap<String, Object> result = new ObjectMapper().readValue(payload, HashMap.class);

			Date tokenExpiresAt = new Date(Long.parseLong(result.get("exp").toString()) * 1000L);
			int userId = Integer.parseInt(result.get("userId").toString());

			if (new Date().getTime() < tokenExpiresAt.getTime() && userTokenRepository.isUserTokenExists(token)) {
				return true;
			} else if (new Date().getTime() > tokenExpiresAt.getTime()) {
				userTokenRepository.deleteUserToken(userId);
				
			}
		} catch (JsonMappingException e) {
			log.debug("MyJwtTokenValidator Line 61 exception: %s".formatted(e.getMessage()));
		} catch (JsonProcessingException e) {
			log.debug("MyJwtTokenValidator Line 63 exception: %s".formatted(e.getMessage()));
		} catch (ArrayIndexOutOfBoundsException e) {
			log.debug("MyJwtTokenValidator Line 65 exception: %s".formatted(e.getMessage()));
		} catch (Exception e) {
			log.debug("MyJwtTokenValidator Line 67 exception: %s".formatted(e.getMessage()));
		}
		return false;
	}
}
