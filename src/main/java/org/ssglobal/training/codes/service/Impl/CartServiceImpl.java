package org.ssglobal.training.codes.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;

import org.springframework.stereotype.Service;
import org.ssglobal.training.codes.repositories.CartRepository;
import org.ssglobal.training.codes.service.CartService;
import org.ssglobal.training.codes.tables.pojos.Cart;
import lombok.RequiredArgsConstructor;

@Primary
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
	
	@Autowired
	private final CartRepository cartRepository;

	@Override
	public List<Cart> selectCartByUser(Integer userId) {
		return cartRepository.selectCartByUser(userId);
	}

	@Override
	public Cart insertCart(Cart product) {
		return cartRepository.insertCart(product);
	}

	@Override
	public boolean updateproductQuantityInCart(Cart product) {
		return cartRepository.updateproductQuantityInCart(product);
	}

	@Override
	public boolean deleteSelectedProductInCart(Cart cart) {
		return cartRepository.deleteSelectedProductInCart(cart);
	}

	@Override
	public boolean deleteSelectedUsersCart(Integer userId) {
		return cartRepository.deleteSelectedUsersCart(userId);
	}
}
