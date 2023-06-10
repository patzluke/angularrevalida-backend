package org.ssglobal.training.codes.service;

import java.util.List;
import java.util.Map;

import org.ssglobal.training.codes.tables.pojos.Cart;
import org.ssglobal.training.codes.tables.pojos.OrderDetails;
import org.ssglobal.training.codes.tables.pojos.Orders;

public interface OrderDetailsService {
	
	List<OrderDetails> selectOrderDetailsByUser(Integer userId);
	OrderDetails insertOrderDetails(OrderDetails orderDetails);
	boolean deleteOrderDetailsByOrderId(Integer orderId);
}
