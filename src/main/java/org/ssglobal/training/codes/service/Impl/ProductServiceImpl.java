package org.ssglobal.training.codes.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;

import org.springframework.stereotype.Service;
import org.ssglobal.training.codes.repositories.ProductRepository;

import org.ssglobal.training.codes.service.ProductService;
import org.ssglobal.training.codes.tables.pojos.Product;

import lombok.RequiredArgsConstructor;

@Primary
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private final ProductRepository productRepository;
	
	@Override
	public List<Product> selectAllProducts() {
		return productRepository.selectAllProducts();
	}
	
	@Override
	public Product selectProduct(Integer productId) {
		return productRepository.selectProduct(productId);
	}
	
	@Override
	public Product insertProduct(Product product) {
		return productRepository.insertProduct(product);
	}
	
	@Override
	public boolean updateProduct(Product product) {
		return productRepository.updateProduct(product);
	}
	
	@Override
	public boolean deleteProductById(Integer productId) {
		return productRepository.deleteProductById(productId);
	}
}
