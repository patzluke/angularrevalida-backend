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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ssglobal.training.codes.service.ProductService;
import org.ssglobal.training.codes.tables.pojos.Product;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping(value = "/api/products")
@Log4j2
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping(value = "/get/top")
	public ResponseEntity<List<Product>> selectTop5ProductsBysales() {
		return new ResponseEntity<>(productService.selectTop5ProductsBysales(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/get")
	public ResponseEntity<List<Product>> selectAllProducts() {
		return new ResponseEntity<>(productService.selectAllProducts(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/get/category/{categoryId}")
	public ResponseEntity<List<Product>> selectAllProductsByCategory(@PathVariable(name = "categoryId") Integer categoryId) {
		return new ResponseEntity<>(productService.selectAllProductsByCategory(categoryId), HttpStatus.OK);
	}
	
	@GetMapping(value = "/get/pricerange")
	public ResponseEntity<List<Product>> selectAllProductsByPriceRange(@RequestParam(name = "minRange") Double minRange,
																	   @RequestParam(name = "maxRange") Double maxRange,
																	   @RequestParam(name = "categoryId") Integer categoryId) {
		if (categoryId == 0) {
			return new ResponseEntity<>(productService.selectAllProductsByPriceRange(minRange, maxRange), HttpStatus.OK);
		}
		return new ResponseEntity<>(productService.selectAllProductsByPriceRange(minRange, maxRange, categoryId), HttpStatus.OK);
	}
	
	@GetMapping(value = "/get/search")
	public ResponseEntity<List<Product>> selectUserByName(@RequestParam(name = "name") String name) {
		return new ResponseEntity<>(productService.selectProductsByName(name), HttpStatus.OK);
	}
	
	@GetMapping(value = "/get/{productId}")
	public ResponseEntity<Product> selectProductById(@PathVariable(name = "productId") Integer productId) {
		return new ResponseEntity<>(productService.selectProduct(productId), HttpStatus.OK);
	}
	
	@PostMapping(value = "/insert", consumes = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Product> createUser(@RequestBody Product product) {
		try {
			Product newProduct = productService.insertProduct(product);
			if (newProduct != null) {
				return ResponseEntity.ok(newProduct);
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.internalServerError().build();
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping(value = "/update", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity updateProduct(@RequestBody Product product) {
		return productService.updateProduct(product) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping(value = "/delete/{productId}")
	public ResponseEntity deleteUserById(@PathVariable(name = "productId") Integer productId) {
		return productService.deleteProductById(productId) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
	}
}
