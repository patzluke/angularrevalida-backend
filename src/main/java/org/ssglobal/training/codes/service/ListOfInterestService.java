package org.ssglobal.training.codes.service;

import java.util.List;
import java.util.Map;

import org.ssglobal.training.codes.tables.pojos.ListOfInterest;

public interface ListOfInterestService {

	List<Map<String, Object>> selectAllUsersInnerJoinOnTheirInterests();
	ListOfInterest insertCustomerInterest(ListOfInterest interests);
}
