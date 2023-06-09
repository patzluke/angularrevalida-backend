package org.ssglobal.training.codes.service;

import java.util.List;
import java.util.Map;

import org.ssglobal.training.codes.tables.pojos.Cart;

public interface CartService {

	List<Map<String, Object>> selectCartByUser(Integer userId);
	Cart insertCart(Cart product);
	boolean updateproductQuantityInCart(Cart product);
	boolean deleteSelectedProductInCart(Integer productId);
	boolean deleteSelectedUsersCart(Integer userId);
}
