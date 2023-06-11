package org.ssglobal.training.codes.controller;

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
import org.ssglobal.training.codes.service.CartService;
import org.ssglobal.training.codes.service.OrderDetailsService;
import org.ssglobal.training.codes.service.ProductService;
import org.ssglobal.training.codes.tables.pojos.OrderDetails;
import org.ssglobal.training.codes.tables.pojos.Product;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping(value = "/api/orderdetails")
@Log4j2
public class OrderDetailsController {

	@Autowired
	private OrderDetailsService orderDetailsService;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private ProductService productService;

	@GetMapping(value = "/get/{userId}")
	public ResponseEntity<List<OrderDetails>> selectOrderDetailsByUser(@PathVariable(name = "userId") Integer userId) {
		List<OrderDetails> cartList = orderDetailsService.selectOrderDetailsByUser(userId);
		return !cartList.isEmpty() ? new ResponseEntity<>(cartList, HttpStatus.OK) : ResponseEntity.notFound().build();
	}
	
	@SuppressWarnings({ "rawtypes" })
	@PostMapping(value = "/insert", consumes = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity insertOrderDetails(@RequestBody List<OrderDetails> orderDetails) {
		try {
			if (!orderDetails.isEmpty()) {
				Integer userId = orderDetails.get(0).getUserId();
				orderDetails.forEach((orderDetail) -> {
					orderDetailsService.insertOrderDetails(orderDetail);
					Product productQuantityUpdate =  productService.selectProduct(orderDetail.getProductId());
					productQuantityUpdate.setQuantity(productQuantityUpdate.getQuantity() - orderDetail.getQuantity());
					productQuantityUpdate.setSales(productQuantityUpdate.getSales() + orderDetail.getQuantity());
					productService.updateProductQuantity(productQuantityUpdate);
				});
				cartService.deleteSelectedUsersCart(userId);
				return ResponseEntity.ok().build();
			}
			return ResponseEntity.badRequest().build();
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		return ResponseEntity.internalServerError().build();
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping(value = "/delete/{orderId}")
	public ResponseEntity deleteOrderDetailsByOrderId(@PathVariable(name = "orderId") Integer orderId) {
		return orderDetailsService.deleteOrderDetailsByOrderId(orderId) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
	}
}