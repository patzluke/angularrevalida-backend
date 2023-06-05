package org.ssglobal.training.codes.service;

import java.util.List;

import org.ssglobal.training.codes.tables.pojos.Product;

public interface ProductService {

	List<Product> selectAllProducts();
	Product selectProduct(Integer productId);
	Product insertProduct(Product product);
	boolean updateProduct(Product product);
	boolean deleteProductById(Integer productId);
}
