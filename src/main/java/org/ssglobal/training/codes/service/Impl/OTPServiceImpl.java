package org.ssglobal.training.codes.service.Impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.ssglobal.training.codes.repositories.OTPRepository;
import org.ssglobal.training.codes.service.OTPService;
import org.ssglobal.training.codes.tables.pojos.Otp;

import lombok.RequiredArgsConstructor;

@Service
@Primary
@RequiredArgsConstructor
public class OTPServiceImpl implements OTPService {

	@Autowired
	private final OTPRepository otpRepository;
	
	@Override
	public Otp createOTP(Otp otp) {
		return otpRepository.createOTP(otp);
	}
	
	@Override
	public Otp selectOtp(Integer userId) {
		return otpRepository.selectOtp(userId);
	}
}
