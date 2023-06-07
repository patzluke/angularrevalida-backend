package org.ssglobal.training.codes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ssglobal.training.codes.models.EmailDetails;
import org.ssglobal.training.codes.service.EmailService;

@RestController
@RequestMapping(value = "/api")
public class EmailController {

	@Autowired
	private EmailService emailService;
}
