package org.ssglobal.training.codes.service;

import java.util.List;

import org.ssglobal.training.codes.tables.pojos.Cart;

public interface CartService {

	List<Cart> selectCartByUser(Integer userId);
	Cart insertCart(Cart product);
	boolean updateproductQuantityInCart(Cart product);
	boolean deleteSelectedProductInCart(Cart cart);
	boolean deleteSelectedUsersCart(Integer userId);
}
