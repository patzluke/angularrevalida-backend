package org.ssglobal.training.codes.service;

import org.ssglobal.training.codes.tables.pojos.Otp;

public interface OTPService {

	Otp createOTP(Otp otp);
	Otp selectOtp(Integer userId);
	boolean deleteOtp(Integer otpId);
	Otp updateOTP(Otp otp);
}
