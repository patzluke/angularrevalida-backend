package org.ssglobal.training.codes.service.Impl;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.crypto.KeyGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.ssglobal.training.codes.repositories.UserRepository;
import org.ssglobal.training.codes.repositories.UserTokenRepository;
import org.ssglobal.training.codes.service.UserService;
import org.ssglobal.training.codes.tables.pojos.Users;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@Primary
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	@Autowired
	private final UserRepository userRepository;
	
	
	@Autowired
	private final UserTokenRepository userTokenRepository;
	
	
	@Override
	public List<Users> selectAllCustomers() {
		return userRepository.selectAllCustomers();
	}
	
	@Override
	public Users selectUser(Integer userId) {
		return userRepository.selectUser(userId);
	}
	
	@Override
	public List<Users> selectUserByName(String search) {
		return userRepository.selectUserByName(search);
	}

	@Override
	public Users insertUser(Users user) {
		user.setPassword(encoder().encode(user.getPassword()));
		return userRepository.insertUser(user);
	}
	
	@Override
	public boolean updateUser(Users user) {
		return userRepository.updateUser(user);
	}
	
	@Override
	public boolean changeCustomerActiveStatus(Boolean userState, Integer userId) {
		return userRepository.changeCustomerActiveStatus(userState, userId);
	}
	
	@Override
	public boolean deleteUserById(Integer userId) {
		return userRepository.deleteUserById(userId);
	}
	
	
	@Override
	public Users searchUserByEmailAndPass(Map<String, String> parameter) {
		Optional<Users> user = Optional.ofNullable(userRepository.searchUserByEmailAndPass(parameter));
		if (user.isEmpty()) {
			return null;
		}
		else if (encoder().matches(parameter.get("password"), user.get().getPassword())) {
			return user.get();
		}
		return null;
	}
	
	@Override
	public boolean changePassword(String password, String username) {
		return userRepository.changePassword(encoder().encode(password), username);
	}
	
	@Override
	public String generateToken(Integer userId, String username, String userType, Boolean isActive) {
		KeyGenerator keyGenerator = null;
		try {
			keyGenerator = KeyGenerator.getInstance("HmacSHA256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		Key key = keyGenerator.generateKey();
		String jwtToken = Jwts.builder()
							  .claim("userId", userId)
							  .claim("username", username)
							  .claim("userType", userType)
							  .claim("isActive", isActive)
							  .setIssuedAt(new Date())
							  .setExpiration(Date.from(LocalDateTime.now().plusMinutes(30L).atZone(ZoneId.systemDefault()).toInstant()))
							  .signWith(key, SignatureAlgorithm.HS256)
							  .compact();
		if (userTokenRepository.isUserTokenIdExists(userId)) {
			userTokenRepository.deleteUserToken(userId);
		}
		userTokenRepository.createToken(userId, jwtToken);
		return jwtToken;
	}
	
	@Bean
	public PasswordEncoder encoder() {
	    return new BCryptPasswordEncoder();
	}
}
