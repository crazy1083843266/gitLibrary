package com.jt.web.service;

import java.util.List;

import com.jt.web.pojo.Cart;

public interface CartService {

	List<Cart> findCartListByUserId(Long userId);

	void saveCart(Cart cart);

	void update(Long userId, Long itemId, Integer num);

	void deleteCart(Long userId, Long itemId);

}
