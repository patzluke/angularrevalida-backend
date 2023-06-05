package org.ssglobal.training.codes.repositories;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Repository
@Transactional
@RequiredArgsConstructor
public class UserTokenRepository {
	
	private final org.ssglobal.training.codes.tables.UserTokens USERTOKENS = org.ssglobal.training.codes.tables.UserTokens.USER_TOKENS;
	
	@Autowired
	private DSLContext dslContext;

	public boolean createToken(Integer employeeId, String token) {
		return dslContext.insertInto(USERTOKENS)
						 .set(USERTOKENS.USER_ID, employeeId)
						 .set(USERTOKENS.TOKEN, token)
						 .execute() == 1 ? true : false;
	}
	
	public boolean updateUserToken(Integer employeeId, String token) {
		return dslContext.update(USERTOKENS)
				 .set(USERTOKENS.TOKEN, token)
				 .where(USERTOKENS.USER_ID.eq(employeeId))
				 .execute() == 1 ? true : false;
	}
	
	public boolean deleteUserToken(Integer userId) {
		return dslContext.deleteFrom(USERTOKENS)
						 .where(USERTOKENS.USER_ID.eq(userId))
						 .execute() == 1 ? true : false;
	}

	public boolean isUserTokenIdExists(Integer userId) {
		return dslContext.fetchExists(USERTOKENS, USERTOKENS.USER_ID.eq(userId));
	}

	public boolean isUserTokenExists(String token) {
		return dslContext.fetchExists(USERTOKENS, USERTOKENS.TOKEN.eq(token));
	}
}
