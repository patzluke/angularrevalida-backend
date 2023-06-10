package org.ssglobal.training.codes.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;

import org.springframework.stereotype.Service;
import org.ssglobal.training.codes.repositories.OrderRepository;
import org.ssglobal.training.codes.service.OrderService;
import org.ssglobal.training.codes.tables.pojos.Orders;

import lombok.RequiredArgsConstructor;

@Primary
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private final OrderRepository orderRepository;

	@Override
	public List<Orders> selectOrdersByUser(Integer userId) {
		return orderRepository.selectOrdersByUser(userId);
	}

	@Override
	public Orders insertOrder(Orders order) {
		return orderRepository.insertOrder(order);
	}

	@Override
	public boolean deleteOrdersById(Integer orderId) {
		return orderRepository.deleteOrdersById(orderId);
	}
}
