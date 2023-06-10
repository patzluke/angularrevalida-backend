package org.ssglobal.training.codes.controller;

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
import org.ssglobal.training.codes.tables.pojos.Cart;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping(value = "/api/cart")
@Log4j2
public class CartController {

	@Autowired
	private CartService cartService;

	@GetMapping(value = "/get/{userId}")
	public ResponseEntity<List<Map<String, Object>>> selectCartByUser(@PathVariable(name = "userId") Integer userId) {
		List<Map<String, Object>> cartList = cartService.selectCartByUser(userId);
		return !cartList.isEmpty() ? new ResponseEntity<>(cartList, HttpStatus.OK) : ResponseEntity.notFound().build();
	}
	
	@PostMapping(value = "/insert", consumes = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Cart> createUser(@RequestBody Cart product) {
		try {
			Cart addedProduct = cartService.insertCart(product);
			if (addedProduct != null) {
				return ResponseEntity.ok(addedProduct);
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.internalServerError().build();
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping(value = "/update", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity updateProduct(@RequestBody Cart product) {
		return cartService.updateproductQuantityInCart(product) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping(value = "/delete/{productId}")
	public ResponseEntity deleteProductUserById(@PathVariable(name = "productId") Integer productId) {
		return cartService.deleteSelectedProductInCart(productId) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping(value = "/delete/user/{userId}")
	public ResponseEntity deleteCartUserById(@PathVariable(name = "userId") Integer userId) {
		try {
			cartService.deleteSelectedUsersCart(userId);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.badRequest().build();
	}
}