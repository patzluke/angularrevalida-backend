package org.ssglobal.training.codes.repositories;

import java.util.List;

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

	@Autowired
	private final DSLContext dslContext;
	
	public List<Cart> selectCartByUser(Integer userId) {
		return dslContext.selectFrom(CART).where(CART.USER_ID.eq(userId))
				.fetchInto(Cart.class);
	}
	
	public Cart insertCart(Cart product) {
		return dslContext.insertInto(CART)
				  .set(CART.USER_ID, product.getUserId())
				  .set(CART.PRODUCT_ID, product.getProductId())
				  .set(CART.QUANTITY, product.getQuantity())
				  .set(CART.PRICE, product.getPrice())
				  .set(CART.PRODUCT_NAME, product.getProductName())
				  .set(CART.VARIATION, product.getVariation())
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
				 					   .set(CART.PRICE, product.getPrice())
				 					   .set(CART.PRODUCT_NAME, product.getProductName())
				 					   .set(CART.VARIATION, product.getVariation())
				 					   .set(CART.TOTAL_PRODUCT_PRICE, product.getTotalProductPrice())
				 					   .set(CART.IMAGE, product.getImage())
									   .where(CART.PRODUCT_ID.eq(product.getProductId())
									   .and(CART.CART_ID.eq(product.getCartId()))
									   ).execute() == 1;
		return updateUser ? true : false;	
	}
	
	public boolean deleteSelectedProductInCart(Cart cart) {
		if (dslContext.delete(CART).where(CART.PRODUCT_ID.eq(cart.getProductId())
								   .and(CART.CART_ID.eq(cart.getCartId()))
								   ).execute() == 1) {
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
