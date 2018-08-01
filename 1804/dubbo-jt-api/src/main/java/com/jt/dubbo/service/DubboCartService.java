package com.jt.dubbo.service;

import java.util.List;

import com.jt.dubbo.pojo.Cart;

public interface DubboCartService {
	//根据用户Id查询购物车信息
	List<Cart> findCartByUserId(Long userId);

	void saveCart(Cart cart);

	void updateCartNum(Long userId, Long itemId, Integer num);

	void deleteCart(Cart cart);

	

	
}
