package org.ssglobal.training.codes.repositories;


import java.util.List;
import java.util.Map;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ssglobal.training.codes.tables.pojos.ListOfInterest;

import lombok.RequiredArgsConstructor;

@Repository
@Transactional
@RequiredArgsConstructor
public class ListOfInterestRepository {
	private final org.ssglobal.training.codes.tables.ListOfInterest LIST_OF_INTEREST = org.ssglobal.training.codes.tables.ListOfInterest.LIST_OF_INTEREST;
	private final org.ssglobal.training.codes.tables.Users USERS = org.ssglobal.training.codes.tables.Users.USERS;

	@Autowired
	private final DSLContext dslContext;
	
	public List<Map<String, Object>> selectAllUsersInnerJoinOnTheirInterests() {
		List<Map<String, Object>> users = dslContext.select(USERS.USER_ID, USERS.USERNAME, USERS.FIRST_NAME, 
															USERS.MIDDLE_NAME, USERS.LAST_NAME, USERS.EMAIL,  
															LIST_OF_INTEREST.INTEREST)
													.from(USERS)
													.innerJoin(LIST_OF_INTEREST).on(USERS.USER_ID.eq(LIST_OF_INTEREST.USER_ID))
													.fetchMaps();
		return users;
	}
	
	public ListOfInterest insertCustomerInterest(ListOfInterest interests) {
		return dslContext.insertInto(LIST_OF_INTEREST)
				  .set(LIST_OF_INTEREST.USER_ID, interests.getUserId())
				  .set(LIST_OF_INTEREST.INTEREST, DSL.aggregate("initcap", String.class, DSL.field("'%s'".formatted(interests.getInterest()))))
				  .returning(LIST_OF_INTEREST.USER_ID, LIST_OF_INTEREST.INTEREST)
				  .fetchOneInto(ListOfInterest.class);
	}
	
//	public boolean updateProduct(Product product) {
//		boolean updateUser = dslContext.update(PRODUCT)
//									   .set(PRODUCT.PRODUCT_NAME, DSL.aggregate("initcap", String.class, DSL.field("'%s'".formatted(product.getProductName()))))
//									   .set(PRODUCT.PRODUCT_DETAILS, DSL.aggregate("initcap", String.class, DSL.field("'%s'".formatted(product.getProductDetails()))))
//									   .set(PRODUCT.INGREDIENTS, product.getIngredients())
//									   .set(PRODUCT.QUANTITY, product.getQuantity())
//									   .set(PRODUCT.PRICE, product.getPrice())
//									   .set(PRODUCT.CATEGORY_ID, product.getCategoryId())
//									   .where(PRODUCT.PRODUCT_ID.eq(product.getProductId()))
//									   .execute() == 1;
//	
//		return updateUser ? true : false;	
//	}
}
