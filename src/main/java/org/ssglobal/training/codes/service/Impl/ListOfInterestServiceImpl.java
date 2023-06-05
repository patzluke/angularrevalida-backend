package org.ssglobal.training.codes.service.Impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;

import org.springframework.stereotype.Service;
import org.ssglobal.training.codes.repositories.ListOfInterestRepository;
import org.ssglobal.training.codes.service.ListOfInterestService;
import org.ssglobal.training.codes.tables.pojos.ListOfInterest;

import lombok.RequiredArgsConstructor;

@Primary
@Service
@RequiredArgsConstructor
public class ListOfInterestServiceImpl implements ListOfInterestService {
	
	@Autowired
	private final ListOfInterestRepository listOfInterestRepository;
	
@Override
	public List<Map<String, Object>> selectAllUsersInnerJoinOnTheirInterests() {
		return listOfInterestRepository.selectAllUsersInnerJoinOnTheirInterests();
	}
	
	@Override
	public ListOfInterest insertCustomerInterest(ListOfInterest interests) {
		return listOfInterestRepository.insertCustomerInterest(interests);
	}
}
