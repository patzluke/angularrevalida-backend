package org.ssglobal.training.codes.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ssglobal.training.codes.models.EmailDetails;
import org.ssglobal.training.codes.service.EmailService;
import org.ssglobal.training.codes.service.OrderService;
import org.ssglobal.training.codes.service.UserService;
import org.ssglobal.training.codes.tables.pojos.Orders;
import org.ssglobal.training.codes.tables.pojos.Users;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping(value = "/api/orders")
@Log4j2
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private UserService userService;

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
				Users user = userService.selectUser(addedOrder.getUserId());
				EmailDetails emailDetails = new EmailDetails();
				emailDetails.setRecipient(user.getEmail());
				emailDetails.setSubject("Your Order From Kahit Saan - #%s - Placed on %s".formatted(addedOrder.getOrderId(), 
																						 addedOrder.getCreatedAt()
																						 .format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a"))
																						 )
										);
				emailDetails.setMsgBody("""
						%s,
						Thank you for ordering from Kahit Saan.
						Your order is on its way. Your order details can be found below.
						
						TRACK YOUR ORDER: http://localhost:4200/order
						
						ORDER SUMMARY:
						
						Order #: %s
						Order Date: %s
						Order Total: %s
						
						SHIPPING ADDRESS: %s
						
						We hope that you will enjoy your ordered food. Please visit us again soon.
						""".formatted(user.getFirstName(), addedOrder.getOrderId(), 
									  addedOrder.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a")), 
									  addedOrder.getTotalPrice(), user.getAddress()));
				emailService.sendSimpleMail(emailDetails);
				
				return ResponseEntity.ok(addedOrder);
			}
			return ResponseEntity.badRequest().build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.internalServerError().build();
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping(value = "/delete/{orderId}")
	public ResponseEntity deleteOrdersById(@PathVariable(name = "orderId") Integer orderId) {
		return orderService.deleteOrdersById(orderId) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
	}
}