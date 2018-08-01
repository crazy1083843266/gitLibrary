package com.jt.cart.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.jt.cart.mapper.CartMapper;
import com.jt.dubbo.pojo.Cart;
import com.jt.dubbo.service.DubboCartService;

public class DubboCartServiceImpl implements DubboCartService {

	@Autowired
	private CartMapper cartMapper;

	@Override
	public List<Cart> findCartByUserId(Long userId) {
		Cart cart = new Cart();
		cart.setUserId(userId);

		return cartMapper.select(cart);
	}

	@Override
	public void saveCart(Cart cart) {
		// 查询购物车数据
		Cart cartDB = cartMapper.findCartByUI(cart);

		if (cartDB == null) {
			cart.setCreated(new Date());
			cart.setUpdated(cart.getCreated());
			cartMapper.insert(cart);
		} else {
			int num = cartDB.getNum() + cart.getNum();
			cartDB.setNum(num);
			cartDB.setUpdated(new Date());
			cartMapper.updateByPrimaryKeySelective(cartDB);
		}

	}

	@Override
	public void updateCartNum(Long userId, Long itemId, Integer num) {
		Cart cart = cartMapper.select(userId, itemId);
		cart.setNum(num);
		cart.setUpdated(new Date());
		cartMapper.insert(cart);
	}


	@Override
	public void deleteCart(Cart cart) {
		cartMapper.delete(cart);
		
	}

	
}
