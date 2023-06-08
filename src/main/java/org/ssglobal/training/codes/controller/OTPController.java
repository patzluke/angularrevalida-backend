package org.ssglobal.training.codes.controller;

import java.time.LocalTime;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ssglobal.training.codes.service.OTPService;
import org.ssglobal.training.codes.service.UserService;
import org.ssglobal.training.codes.tables.pojos.Otp;

@RestController
@RequestMapping(value = "/api/otp")

public class OTPController {

	@Autowired
	private OTPService otpService;
	
	@Autowired
	private UserService userService;
	@PostMapping(value = "/validate", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<String> createUser(@RequestBody Map<String, Object> payload) {
		Otp otp = otpService.selectOtp(Integer.parseInt(payload.get("userId").toString()));
		try {
			if (otp != null) {
				if (otp.getOtpCode().equals(payload.get("otpCode"))) {
					if (LocalTime.now().compareTo(otp.getExpiryTime()) > 0) {
						userService.deleteUserById(Integer.parseInt(payload.get("userId").toString()));
						return ResponseEntity.badRequest().body("token expired");
					}
					userService.changeCustomerActiveStatus(true, 
								Integer.parseInt(payload.get("userId").toString()));
					otpService.deleteOtp(otp.getOtpId());
					return ResponseEntity.ok("true");
				}
				return ResponseEntity.badRequest().body("wrong token");
			}
			return ResponseEntity.badRequest().body("wrong token");
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}

}
