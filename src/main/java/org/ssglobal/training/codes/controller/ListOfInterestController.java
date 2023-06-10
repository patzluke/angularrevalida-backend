package org.ssglobal.training.codes.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ssglobal.training.codes.service.ListOfInterestService;

@RestController
@RequestMapping(value = "/api/interests")
public class ListOfInterestController {

	@Autowired
	private ListOfInterestService listOfInterestService;
	
	@GetMapping(value = "/get")
	public ResponseEntity<List<Map<String, Object>>> selectAllUsers() {
		return new ResponseEntity<>(listOfInterestService.selectAllUsersInnerJoinOnTheirInterests(), HttpStatus.OK);
	}
}
