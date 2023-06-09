package org.ssglobal.training.codes.repositories;

import java.util.List;
import java.util.Map;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ssglobal.training.codes.tables.pojos.Cart;

import lombok.RequiredArgsConstructor;

@Repository
@Transactional
@RequiredArgsConstructor
public class CartRepository {
	private final org.ssglobal.training.codes.tables.Cart CART = org.ssglobal.training.codes.tables.Cart.CART;
	private final org.ssglobal.training.codes.tables.Product PRODUCT = org.ssglobal.training.codes.tables.Product.PRODUCT;

	@Autowired
	private final DSLContext dslContext;
	
	public List<Map<String, Object>> selectCartByUser(Integer userId) {
		return dslContext.select(CART.USER_ID.as("userId"), CART.PRODUCT_ID.as("productId"), CART.QUANTITY.as("quantity"), 
								 CART.TOTAL_PRODUCT_PRICE.as("totalProductPrice"), CART.IMAGE.as("image"), PRODUCT.PRICE.as("price"),
								 PRODUCT.PRODUCT_NAME.as("productName"))
								 .from(CART.innerJoin(PRODUCT).on(CART.PRODUCT_ID.eq(PRODUCT.PRODUCT_ID)))
								 .where(CART.USER_ID.eq(userId))
								 .fetchMaps();
	}
	
	public Cart insertCart(Cart product) {
		return dslContext.insertInto(CART)
				  .set(CART.USER_ID, product.getUserId())
				  .set(CART.PRODUCT_ID, product.getProductId())
				  .set(CART.QUANTITY, product.getQuantity())
				  .set(CART.TOTAL_PRODUCT_PRICE, product.getTotalProductPrice())
				  .set(CART.IMAGE, product.getImage())
				  .returning(CART.USER_ID, CART.PRODUCT_ID, CART.QUANTITY, 
						  CART.TOTAL_PRODUCT_PRICE, CART.IMAGE)
				  .fetchOneInto(Cart.class);
	}
	
	public boolean updateproductQuantityInCart(Cart product) {
		boolean updateUser = dslContext.update(CART)
				 					   .set(CART.USER_ID, product.getUserId())
				 					   .set(CART.PRODUCT_ID, product.getProductId())
				 					   .set(CART.QUANTITY, product.getQuantity())
				 					   .set(CART.TOTAL_PRODUCT_PRICE, product.getTotalProductPrice())
				 					   .set(CART.IMAGE, product.getImage())
									   .where(CART.PRODUCT_ID.eq(product.getProductId()))
									   .execute() == 1;
		return updateUser ? true : false;	
	}
	
	public boolean deleteSelectedProductInCart(Integer productId) {
		if (dslContext.delete(CART).where(CART.PRODUCT_ID.eq(productId)).execute() == 1) {
			return true;
		}
		return false;
	}
	
	public boolean deleteSelectedUsersCart(Integer userId) {
		if (dslContext.delete(CART).where(CART.USER_ID.eq(userId)).execute() == 1) {
			return true;
		}
		return false;
	}
}
