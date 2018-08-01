package com.jt.cart.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.cart.mapper.CartMapper;
import com.jt.cart.pojo.Cart;

@Service
public class CartServiceImpl implements CartService{
	@Autowired 
	private CartMapper cartMapper;
	
	
	@Override
	public List<Cart> findCartByUserId(Long userId) {
		Cart cart=new Cart();
		cart.setUserId(userId);
		return cartMapper.select(cart);
	}
	
	/**
	 * 1.根据UserId和ItemId查询购物车数据、
	 * 如果购物车有数据，则应该做购物车更新商品数量
	 * 如果购物车没有数据，则做购物车入库操作
	 */
	@Override
	public void saveCart(Cart cart) {
		
		Cart cartDB=cartMapper.findCartByUI(cart);
		Date date=new Date();
		if(cartDB ==null){
			cart.setCreated(date);
			cart.setCreated(date);
			cartMapper.insert(cart);
			
		}else{
			int num=cartDB.getNum()+cart.getNum();
			
			cartDB.setNum(num);
			cartDB.setUpdated(date);
			cartMapper.updateByPrimaryKey(cartDB);
		}
	}

	@Override
	public void update(Long userId, Long itemId, Integer num) {
		
		Cart cart=new Cart();
		cart.setUserId(userId);
		cart.setItemId(itemId);
		List<Cart> cartTemp = cartMapper.select(cart);
		for (Cart cart2 : cartTemp) {
			
			if(cart2==null){
				throw new RuntimeException();
			}
			cart2.setNum(num);
			cart2.setUpdated(new Date());
			cartMapper.insert(cart2);
		}
		
	}

	@Override
	public void deleteCart(Long userId, Long itemId) {
		
		cartMapper.deleteCart(userId,itemId);
		
	}

}
