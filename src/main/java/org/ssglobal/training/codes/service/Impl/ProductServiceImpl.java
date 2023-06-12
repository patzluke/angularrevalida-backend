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
	public List<Product> selectTop5ProductsBysales() {
		return productRepository.selectTop5ProductsBysales();
	}
	
	@Override
	public List<Product> selectAllProducts() {
		return productRepository.selectAllProducts();
	}
	

	@Override
	public List<Product> selectAllProductsByCategory(Integer categoryId) {
		return productRepository.selectAllProductsByCategory(categoryId);
	}
	
	@Override
	public List<Product> selectAllProductsByPriceRange(Double minRange, Double maxRange, Integer categoryId) {
		return productRepository.selectAllProductsByPriceRange(minRange, maxRange, categoryId);
	}
	
	@Override
	public List<Product> selectAllProductsByPriceRange(Double minRange, Double maxRange) {
		return productRepository.selectAllProductsByPriceRange(minRange, maxRange);
	}
	
	@Override
	public List<Product> selectProductsByName(String search) {
		return productRepository.selectProductsByName(search);
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
	public boolean updateProductQuantity(Product product) {
		return productRepository.updateProductQuantity(product);
	}
	
	@Override
	public boolean deleteProductById(Integer productId) {
		return productRepository.deleteProductById(productId);
	}
}
