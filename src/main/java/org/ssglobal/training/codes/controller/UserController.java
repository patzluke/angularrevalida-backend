package org.ssglobal.training.codes.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ssglobal.training.codes.models.EmailDetails;
import org.ssglobal.training.codes.service.EmailService;
import org.ssglobal.training.codes.service.ListOfInterestService;
import org.ssglobal.training.codes.service.OTPService;
import org.ssglobal.training.codes.service.UserService;
import org.ssglobal.training.codes.service.Impl.EmailServiceImpl;
import org.ssglobal.training.codes.tables.pojos.ListOfInterest;
import org.ssglobal.training.codes.tables.pojos.Otp;
import org.ssglobal.training.codes.tables.pojos.Users;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping(value = "/api/users")
@Log4j2
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ListOfInterestService listOfInterestService;
	
	@Autowired
	private OTPService otpService;
	
	@Autowired
	private EmailServiceImpl emailService;

	@GetMapping(value = "/get")
	public ResponseEntity<List<Users>> selectAllUsers() {
		return new ResponseEntity<>(userService.selectAllCustomers(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/get/search")
	public ResponseEntity<List<Users>> selectUserByName(@RequestParam(name = "name") String name) {
		return new ResponseEntity<>(userService.selectUserByName(name), HttpStatus.OK);
	}
	
	@GetMapping(value = "/get/{userId}")
	public ResponseEntity<Users> selectUserById(@PathVariable(name = "userId") Integer userId) {
		return new ResponseEntity<>(userService.selectUser(userId), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "null", "unused" })
	@PostMapping(value = "/insert", consumes = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Users> createUser(@RequestBody Map<String, Object> user) {	
		Users newUser = new Users(null, user.get("username").toString(), user.get("password").toString(), 
								user.get("firstName").toString(), user.get("middleName").toString(), 
								user.get("lastName").toString(), user.get("email").toString(), 
								user.get("address").toString(), user.get("contactNo").toString(), 
								LocalDate.parse(user.get("birthDate").toString()), user.get("userType").toString(), 
								Boolean.valueOf(user.get("isActive").toString()));
		try {
			Users getNewUser = userService.insertUser(newUser);
			if (getNewUser != null) {
				new ArrayList<>((ArrayList<Object>) user.get("listOfInterest")).forEach(data -> {
					ListOfInterest userInterest = new ListOfInterest(getNewUser.getUserId(), data.toString());
					listOfInterestService.insertCustomerInterest(userInterest);
				});
			}
			else {
				return ResponseEntity.badRequest().build();
			}
			LocalTime issuedTime = LocalTime.now();
			LocalTime expiryTime = issuedTime.plusMinutes(5);
	        String sixRandomNumber = RandomStringUtils.randomNumeric(6);
	        Otp otp = new Otp(null, getNewUser.getUserId(), issuedTime, expiryTime, sixRandomNumber);
			Otp createdOtp = otpService.createOTP(otp);
			if (otp != null) {
				EmailDetails emailDetails = new EmailDetails();
				emailDetails.setRecipient(newUser.getEmail());
				emailDetails.setSubject("OTP Verification");
				emailDetails.setMsgBody("""
						Hi %s,
						Thank you for choosing our restaurant. Use The Otp below to complete the procedures.
						It is valid for only 5 minutes!
						The verification code is: %s
						
						Regards,
						Jobilee
						""".formatted(newUser.getFirstName(), createdOtp.getOtpCode()));
				emailService.sendSimpleMail(emailDetails);
				return ResponseEntity.ok(getNewUser);
			}
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.internalServerError().build();
	}
	
	@PostMapping(value = "/authenticate", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Object>> authenticate(@RequestBody Map<String, String> payload) {
		Users authenticatedUser = userService.searchUserByEmailAndPass(payload);
		if (authenticatedUser != null) {
			List<Object> usertoken = new ArrayList<>();
			String token = userService.generateToken(authenticatedUser.getUserId(), 
													 authenticatedUser.getUsername(), 
													 authenticatedUser.getUserType(),
													 authenticatedUser.getIsActive());
			usertoken.add(token);
			return new ResponseEntity<>(usertoken, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping(value = "/update", consumes = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity updateUser(@RequestBody Users user) {
		return userService.updateUser(user) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping(value = "/update/activestatus", consumes = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity changeCustomerActiveStatus(@RequestBody Map<String, Object> payload) {
		return userService.changeCustomerActiveStatus(Boolean.parseBoolean(payload.get("isActive").toString()), 
											  Integer.parseInt(payload.get("userId").toString())) 
				
				? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping(value = "/delete/{userId}")
	public ResponseEntity deleteUserById(@PathVariable(name = "userId") Integer userId) {
		return userService.deleteUserById(userId) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping(value = "/update/password", consumes = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity changePassword(@RequestBody Map<String, String> payload) {
		return userService.changePassword(payload.get("password"), payload.get("username")) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
	}
}
