package org.ssglobal.training.codes.repositories;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.ssglobal.training.codes.tables.pojos.Otp;
import org.ssglobal.training.codes.tables.pojos.Users;

@Repository
public class OTPRepository {
	
	private final org.ssglobal.training.codes.tables.Otp OTP = org.ssglobal.training.codes.tables.Otp.OTP;


	@Autowired
	private DSLContext dslContext;
	
	public Otp createOTP(Otp otp) {
		return dslContext.insertInto(OTP)
						 .set(OTP.USER_ID, otp.getUserId())
						 .set(OTP.ISSUED_TIME, otp.getIssuedTime())
						 .set(OTP.EXPIRY_TIME, otp.getExpiryTime())
						 .set(OTP.OTP_CODE, otp.getOtpCode())
						 .returning(OTP.OTP_ID, OTP.USER_ID, OTP.ISSUED_TIME, OTP.EXPIRY_TIME, OTP.OTP_CODE)
						 .fetchOneInto(Otp.class);
	}
	
	public Otp selectOtp(Integer userId) {
		return dslContext.selectFrom(OTP).where(OTP.USER_ID.eq(userId)).fetchOneInto(Otp.class);
	}
}
