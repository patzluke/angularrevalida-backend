package org.ssglobal.training.codes.repositories;

import java.util.List;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ssglobal.training.codes.tables.pojos.Product;

import lombok.RequiredArgsConstructor;

@Repository
@Transactional
@RequiredArgsConstructor
public class ProductRepository {
	private final org.ssglobal.training.codes.tables.Product PRODUCT = org.ssglobal.training.codes.tables.Product.PRODUCT;

	@Autowired
	private final DSLContext dslContext;
	
	public List<Product> selectAllProducts() {
		List<Product> products = dslContext.selectFrom(PRODUCT).fetchInto(Product.class);
		return products;
	}
	
	public List<Product> selectAllProductsByCategory(Integer categoryId) {
		List<Product> products = dslContext.selectFrom(PRODUCT)
										   .where(PRODUCT.CATEGORY_ID.eq(categoryId))
										   .fetchInto(Product.class);
		return products;
	}
	
	public List<Product> selectProductsByName(String search) {
		return dslContext.selectFrom(PRODUCT)
						 .where(PRODUCT.PRODUCT_NAME.likeIgnoreCase(DSL.concat("%", search, "%")))
						 .fetchInto(Product.class);
	}
	
	public Product selectProduct(Integer productId) {
		return dslContext.selectFrom(PRODUCT).where(PRODUCT.PRODUCT_ID.eq(productId)).fetchOneInto(Product.class);
	}
	
	public Product insertProduct(Product product) {
		return dslContext.insertInto(PRODUCT)
				  .set(PRODUCT.PRODUCT_NAME, DSL.aggregate("initcap", String.class, DSL.field("'%s'".formatted(product.getProductName()))))
				  .set(PRODUCT.PRODUCT_DETAILS, DSL.aggregate("initcap", String.class, DSL.field("'%s'".formatted(product.getProductDetails()))))
				  .set(PRODUCT.INGREDIENTS, product.getIngredients())
				  .set(PRODUCT.QUANTITY, product.getQuantity())
				  .set(PRODUCT.PRICE, product.getPrice())
				  .set(PRODUCT.CATEGORY_ID, product.getCategoryId())
				  .set(PRODUCT.IMAGE, product.getImage())
				  .returning(PRODUCT.PRODUCT_ID, PRODUCT.PRODUCT_NAME, PRODUCT.PRODUCT_DETAILS, 
						  	 PRODUCT.INGREDIENTS, PRODUCT.QUANTITY, PRODUCT.PRICE, 
						  	 PRODUCT.CATEGORY_ID, PRODUCT.IMAGE)
				  .fetchOneInto(Product.class);
	}
	
	public boolean updateProduct(Product product) {
		boolean updateUser = dslContext.update(PRODUCT)
									   .set(PRODUCT.PRODUCT_NAME, DSL.aggregate("initcap", String.class, DSL.field("'%s'".formatted(product.getProductName()))))
									   .set(PRODUCT.PRODUCT_DETAILS, DSL.aggregate("initcap", String.class, DSL.field("'%s'".formatted(product.getProductDetails()))))
									   .set(PRODUCT.INGREDIENTS, product.getIngredients())
									   .set(PRODUCT.QUANTITY, product.getQuantity())
									   .set(PRODUCT.PRICE, product.getPrice())
									   .set(PRODUCT.CATEGORY_ID, product.getCategoryId())
									   .set(PRODUCT.IMAGE, product.getImage())
									   .where(PRODUCT.PRODUCT_ID.eq(product.getProductId()))
									   .execute() == 1;
	
		return updateUser ? true : false;	
	}
	
	public boolean deleteProductById(Integer productId) {
		if (dslContext.delete(PRODUCT).where(PRODUCT.PRODUCT_ID.eq(productId)).execute() == 1) {
			return true;
		}
		return false;
	}
}
