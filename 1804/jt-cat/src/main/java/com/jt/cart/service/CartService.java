package com.jt.cart.service;

import java.util.List;

import com.jt.cart.pojo.Cart;

public interface CartService {

	List<Cart> findCartByUserId(Long userId);

	void saveCart(Cart cart);

	void update(Long userId, Long itemId, Integer num);

	void deleteCart(Long userId, Long itemId);

}
