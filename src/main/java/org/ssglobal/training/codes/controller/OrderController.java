package org.ssglobal.training.codes.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ssglobal.training.codes.service.CartService;
import org.ssglobal.training.codes.service.OrderService;
import org.ssglobal.training.codes.tables.pojos.Cart;
import org.ssglobal.training.codes.tables.pojos.Orders;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping(value = "/api/orders")
@Log4j2
public class OrderController {

	@Autowired
	private OrderService orderService;

	@GetMapping(value = "/get/{userId}")
	public ResponseEntity<List<Orders>> selectOrderByUser(@PathVariable(name = "userId") Integer userId) {
		List<Orders> cartList = orderService.selectOrdersByUser(userId);
		return !cartList.isEmpty() ? new ResponseEntity<>(cartList, HttpStatus.OK) : ResponseEntity.notFound().build();
	}
	
	@PostMapping(value = "/insert", consumes = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Orders> insertOrder(@RequestBody Orders order) {
		try {
			order.setCreatedAt(LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)));
			Orders addedOrder = orderService.insertOrder(order);
			if (addedOrder != null) {
				return ResponseEntity.ok(addedOrder);
			}
			return ResponseEntity.badRequest().build();
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		return ResponseEntity.internalServerError().build();
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping(value = "/delete/{orderId}")
	public ResponseEntity deleteOrdersById(@PathVariable(name = "orderId") Integer orderId) {
		return orderService.deleteOrdersById(orderId) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
	}
}