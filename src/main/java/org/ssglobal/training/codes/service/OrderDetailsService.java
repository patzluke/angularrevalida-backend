package org.ssglobal.training.codes.service;

import java.util.List;

import org.ssglobal.training.codes.tables.pojos.OrderDetails;

public interface OrderDetailsService {
	
	List<OrderDetails> selectOrderDetailsByOrderId(Integer orderId);
	OrderDetails insertOrderDetails(OrderDetails orderDetails);
	boolean deleteOrderDetailsByOrderId(Integer orderId);
}
