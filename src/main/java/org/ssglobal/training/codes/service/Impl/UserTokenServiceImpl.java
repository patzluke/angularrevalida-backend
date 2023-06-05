package org.ssglobal.training.codes.service.Impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssglobal.training.codes.repositories.UserTokenRepository;
import org.ssglobal.training.codes.service.UserTokenService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserTokenServiceImpl implements UserTokenService {

	@Autowired
	private final UserTokenRepository userTokenRepository;
	
	@Override
	public boolean createToken(Integer employeeId, String token) {
		return userTokenRepository.createToken(employeeId, token);
	}

	@Override
	public boolean updateUserToken(Integer employeeId, String token) {
		return userTokenRepository.updateUserToken(employeeId, token);
	}

	@Override
	public boolean deleteUserToken(Integer userId) {
		return userTokenRepository.deleteUserToken(userId);
	}

	@Override
	public boolean isUserTokenIdExists(Integer userId) {
		return userTokenRepository.isUserTokenIdExists(userId);
	}

	@Override
	public boolean isUserTokenExists(String token) {
		return userTokenRepository.isUserTokenExists(token);
	}
}
