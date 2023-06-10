package org.ssglobal.training.codes.repositories;

import java.util.List;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ssglobal.training.codes.tables.pojos.Orders;

import lombok.RequiredArgsConstructor;

@Repository
@Transactional
@RequiredArgsConstructor
public class OrderRepository {
	private final org.ssglobal.training.codes.tables.Orders ORDERS = org.ssglobal.training.codes.tables.Orders.ORDERS;

	@Autowired
	private final DSLContext dslContext;
	
	public List<Orders> selectOrdersByUser(Integer userId) {
		return dslContext.selectFrom(ORDERS)
								 .where(ORDERS.USER_ID.eq(userId))
								 .fetchInto(Orders.class);
	}
	
	public Orders insertOrder(Orders order) {
		return dslContext.insertInto(ORDERS)
				  .set(ORDERS.USER_ID, order.getUserId())
				  .set(ORDERS.TOTAL_PRICE, order.getTotalPrice())
				  .set(ORDERS.CREATED_AT, order.getCreatedAt())
				  .returning(ORDERS.ORDER_ID, ORDERS.USER_ID, ORDERS.TOTAL_PRICE, ORDERS.CREATED_AT)
				  .fetchOneInto(Orders.class);
	}
	
	public boolean deleteOrdersById(Integer orderId) {
		if (dslContext.deleteFrom(ORDERS).where(ORDERS.ORDER_ID.eq(orderId)).execute() == 1) {
			return true;
		}
		return false;
	}
}
