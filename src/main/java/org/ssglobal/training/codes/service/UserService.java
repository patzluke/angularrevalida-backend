package org.ssglobal.training.codes.service;

import java.util.List;
import java.util.Map;

import org.ssglobal.training.codes.tables.pojos.Users;

public interface UserService {

	List<Users> selectAllCustomers();
	List<Map<String, Object>> selectUserWithListOfInterest(Integer userId);
	Users selectUser(Integer userId);
	List<Users> selectUserByName(String search);
	Users insertUser(Users user);
	boolean updateUser(Users user);
	boolean changeCustomerActiveStatus(Boolean userState, Integer userId);
	boolean deleteUserById(Integer userId);
	Users searchUserByEmailAndPass(Map<String, String> parameter);
	boolean changePassword(String password, String username);
	boolean forgotPassword(String password, String username, String contactNo, String email);
	String generateToken(Integer userId, String username, String userType, Boolean isAtive);

}
