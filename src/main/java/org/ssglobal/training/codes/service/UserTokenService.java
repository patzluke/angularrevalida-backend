package org.ssglobal.training.codes.service;

public interface UserTokenService {

	boolean createToken(Integer employeeId, String token);
	boolean updateUserToken(Integer employeeId, String token);
	boolean deleteUserToken(Integer userId);
	boolean isUserTokenIdExists(Integer userId);
	boolean isUserTokenExists(String token);
}
