package org.ssglobal.training.codes.repositories;

import java.util.List;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ssglobal.training.codes.tables.pojos.OrderDetails;

import lombok.RequiredArgsConstructor;

@Repository
@Transactional
@RequiredArgsConstructor
public class OrderDetailsRepository {
	private final org.ssglobal.training.codes.tables.OrderDetails ORDERDETAILS = org.ssglobal.training.codes.tables.OrderDetails.ORDER_DETAILS;

	@Autowired
	private final DSLContext dslContext;
	
	public List<OrderDetails> selectOrderDetailsByUser(Integer userId) {
		return dslContext.selectFrom(ORDERDETAILS)
								 .where(ORDERDETAILS.USER_ID.eq(userId))
								 .fetchInto(OrderDetails.class);
	}
	
	public OrderDetails insertOrderDetails(OrderDetails orderDetails) {
		return dslContext.insertInto(ORDERDETAILS)
				  .set(ORDERDETAILS.ORDER_ID, orderDetails.getOrderId())
				  .set(ORDERDETAILS.USER_ID, orderDetails.getUserId())
				  .set(ORDERDETAILS.PRODUCT_ID, orderDetails.getProductId())
				  .set(ORDERDETAILS.QUANTITY, orderDetails.getQuantity())
				  .set(ORDERDETAILS.TOTAL_PRODUCT_PRICE, orderDetails.getTotalProductPrice())
				  .set(ORDERDETAILS.IMAGE, orderDetails.getImage())
				  .returning(ORDERDETAILS.USER_ID, ORDERDETAILS.PRODUCT_ID, ORDERDETAILS.QUANTITY, 
						  	 ORDERDETAILS.TOTAL_PRODUCT_PRICE, ORDERDETAILS.IMAGE)
				  .fetchOneInto(OrderDetails.class);
	}
	
	public boolean deleteOrderDetailsByOrderId(Integer orderId) {
		if (dslContext.deleteFrom(ORDERDETAILS).where(ORDERDETAILS.ORDER_ID.eq(orderId)).execute() == 1) {
			return true;
		}
		return false;
	}
}
