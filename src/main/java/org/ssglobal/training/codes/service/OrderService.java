package org.ssglobal.training.codes.service;

import java.util.List;

import org.ssglobal.training.codes.tables.pojos.Orders;

public interface OrderService {

	 List<Orders> selectOrdersByUser(Integer userId);
	 Orders insertOrder(Orders order);
	 boolean deleteOrdersById(Integer orderId);
}
