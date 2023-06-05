package org.ssglobal.training.codes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ssglobal.training.codes.service.UserTokenService;

@RestController
@RequestMapping(value = "/api/usertoken")
public class UserTokenController {
	
	@Autowired
	private UserTokenService userTokenService;
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping(value = "/delete/{userId}")
	public ResponseEntity deleteUserById(@PathVariable(name = "userId") Integer userId) {
		return userTokenService.deleteUserToken(userId) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
	}
}
