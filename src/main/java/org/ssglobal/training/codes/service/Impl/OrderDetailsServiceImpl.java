package org.ssglobal.training.codes.service.Impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;

import org.springframework.stereotype.Service;
import org.ssglobal.training.codes.repositories.CartRepository;
import org.ssglobal.training.codes.repositories.OrderDetailsRepository;
import org.ssglobal.training.codes.repositories.OrderRepository;
import org.ssglobal.training.codes.service.CartService;
import org.ssglobal.training.codes.service.OrderDetailsService;
import org.ssglobal.training.codes.service.OrderService;
import org.ssglobal.training.codes.tables.pojos.Cart;
import org.ssglobal.training.codes.tables.pojos.OrderDetails;
import org.ssglobal.training.codes.tables.pojos.Orders;

import lombok.RequiredArgsConstructor;

@Primary
@Service
@RequiredArgsConstructor
public class OrderDetailsServiceImpl implements OrderDetailsService {
	
	@Autowired
	private final OrderDetailsRepository orderDetailsRepository;

	@Override
	public List<OrderDetails> selectOrderDetailsByUser(Integer userId) {
		return orderDetailsRepository.selectOrderDetailsByUser(userId);
	}

	@Override
	public OrderDetails insertOrderDetails(OrderDetails orderDetails) {
		return orderDetailsRepository.insertOrderDetails(orderDetails);
	}

	@Override
	public boolean deleteOrderDetailsByOrderId(Integer orderId) {
		return orderDetailsRepository.deleteOrderDetailsByOrderId(orderId);
	}
}
