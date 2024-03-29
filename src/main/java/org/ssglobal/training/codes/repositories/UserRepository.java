package org.ssglobal.training.codes.repositories;

import java.util.List;
import java.util.Map;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ssglobal.training.codes.tables.pojos.Users;

import lombok.RequiredArgsConstructor;

@Repository
@Transactional
@RequiredArgsConstructor
public class UserRepository {
	private final org.ssglobal.training.codes.tables.Users USERS = org.ssglobal.training.codes.tables.Users.USERS;
	private final org.ssglobal.training.codes.tables.ListOfInterest LIST_OF_INTEREST = org.ssglobal.training.codes.tables.ListOfInterest.LIST_OF_INTEREST;

	@Autowired
	private final DSLContext dslContext;
	
	public List<Users> selectAllCustomers() {
		List<Users> users = dslContext.selectFrom(USERS)
									  .where(USERS.USER_TYPE.notEqualIgnoreCase("Admin"))
									  .fetchInto(Users.class);
		return users;
	}

	public List<Map<String, Object>> selectUserWithListOfInterest(Integer userId) {
		return dslContext.select(USERS.USER_ID.as("userId"), USERS.USERNAME.as("username"), USERS.PASSWORD.as("password"),
								 USERS.FIRST_NAME.as("firstName"), USERS.MIDDLE_NAME.as("middleName"), USERS.LAST_NAME.as("lastName"), 
								 USERS.EMAIL.as("email"), USERS.ADDRESS.as("address"), USERS.CONTACT_NO.as("contactNo"),
								 USERS.BIRTH_DATE.as("birthDate"), USERS.USER_TYPE.as("userType"), USERS.IS_ACTIVE.as("isActive"), 
								 LIST_OF_INTEREST.INTEREST.as("listOfInterest"))
								 .from(USERS)
								 .innerJoin(LIST_OF_INTEREST).on(USERS.USER_ID.eq(LIST_OF_INTEREST.USER_ID))
								 .where(USERS.USER_ID.eq(userId)).fetchMaps();
	}
	
	public Users selectUser(Integer userId) {
		return dslContext.selectFrom(USERS).where(USERS.USER_ID.eq(userId)).fetchOneInto(Users.class);
	}
	
	public List<Users> selectUserByName(String search) {
		return dslContext.selectFrom(USERS)
						 .where(USERS.FIRST_NAME.likeIgnoreCase(DSL.concat("%", search, "%"))
						 .or(USERS.MIDDLE_NAME.likeIgnoreCase(DSL.concat("%", search, "%"))))
						 .or(USERS.LAST_NAME.likeIgnoreCase(DSL.concat("%", search, "%")))
						 .and(USERS.USER_TYPE.notEqualIgnoreCase("Admin"))
						 .fetchInto(Users.class);
	}
	
	public Users insertUser(Users user) {
		return dslContext.insertInto(USERS)
				  .set(USERS.USERNAME, user.getUsername())
				  .set(USERS.PASSWORD, user.getPassword())
				  .set(USERS.FIRST_NAME, DSL.aggregate("initcap", String.class, DSL.field("'%s'".formatted(user.getFirstName()))))
				  .set(USERS.MIDDLE_NAME, DSL.aggregate("initcap", String.class, DSL.field("'%s'".formatted(user.getMiddleName()))))
				  .set(USERS.LAST_NAME, DSL.aggregate("initcap", String.class, DSL.field("'%s'".formatted(user.getLastName()))))
				  .set(USERS.EMAIL, user.getEmail())
				  .set(USERS.ADDRESS, user.getAddress())
				  .set(USERS.CONTACT_NO, user.getContactNo())
				  .set(USERS.BIRTH_DATE, user.getBirthDate())
				  .set(USERS.USER_TYPE, user.getUserType())
				  .set(USERS.IS_ACTIVE, user.getIsActive())
				  .returning(USERS.USER_ID, USERS.EMAIL)
				  .fetchOneInto(Users.class);
	}
	
	public boolean updateUser(Users user) {
		boolean updateUser = dslContext.update(USERS)
									   .set(USERS.USERNAME, user.getUsername())
									   .set(USERS.FIRST_NAME, DSL.aggregate("initcap", String.class, DSL.field("'%s'".formatted(user.getFirstName()))))
									   .set(USERS.MIDDLE_NAME, DSL.aggregate("initcap", String.class, DSL.field("'%s'".formatted(user.getMiddleName()))))
									   .set(USERS.LAST_NAME, DSL.aggregate("initcap", String.class, DSL.field("'%s'".formatted(user.getLastName()))))
									   .set(USERS.ADDRESS, user.getAddress())
									   .where(USERS.USER_ID.eq(user.getUserId()))
									   .execute() == 1;
		
		return updateUser ? true : false;	
	}
	
	public boolean changeCustomerActiveStatus(Boolean userState, Integer userId) {
		boolean updateUser = dslContext.update(USERS)
									   .set(USERS.IS_ACTIVE, userState)
									   .where(USERS.USER_ID.eq(userId))
									   .execute() == 1;
		
		return updateUser ? true : false;	
	}
	
	public boolean deleteUserById(Integer userId) {
		if (dslContext.delete(USERS).where(USERS.USER_ID.eq(userId)).execute() == 1) {
			return true;
		}
		return false;
	}
	
	public Users searchUserByEmailAndPass(Map<String, String> parameter) {
		Users user = dslContext.select(USERS.USER_ID, USERS.USERNAME, USERS.PASSWORD, USERS.USER_TYPE, USERS.IS_ACTIVE)
											  .from(USERS)
				   							  .where(USERS.USERNAME.eq(parameter.get("username")))
				   							  .fetchOneInto(Users.class);
		return user;
	}
	
	public boolean changePassword(String password, String username) {
		boolean updateUser = dslContext.update(USERS)
									   .set(USERS.PASSWORD, password)
									   .where(USERS.USERNAME.eq(username))
									   .execute() == 1;
		if (updateUser) {
			return true;
		}
		return false;
	}
	
	public boolean forgotPassword(String password, String username, String contactNo, String email) {
		boolean updateUser = dslContext.update(USERS)
									   .set(USERS.PASSWORD, password)
									   .where(USERS.USERNAME.eq(username)
									   .and(USERS.CONTACT_NO.eq(contactNo))
									   .and(USERS.EMAIL.eq(email)))
									   .execute() == 1;
		if (updateUser) {
			return true;
		}
		return false;
	}
}
