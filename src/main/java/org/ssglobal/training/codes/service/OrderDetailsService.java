package org.ssglobal.training.codes.service;

import java.util.List;

import org.ssglobal.training.codes.tables.pojos.OrderDetails;

public interface OrderDetailsService {
	
	List<OrderDetails> selectOrderDetailsByUser(Integer userId);
	OrderDetails insertOrderDetails(OrderDetails orderDetails);
	boolean deleteOrderDetailsByOrderId(Integer orderId);
}
