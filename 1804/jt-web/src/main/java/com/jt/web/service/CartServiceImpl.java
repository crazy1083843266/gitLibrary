package com.jt.web.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.Cart;

@Service
public class CartServiceImpl implements CartService{
	@Autowired
	private HttpClientService httpClient;
	private static ObjectMapper objectMapper=new ObjectMapper();
	
	@Override
	public List<Cart> findCartListByUserId(Long userId) {
		String url="http://cat.jt.com/cart/query/"+userId;
		String jsonData = httpClient.doGet(url);
		List<Cart> cartList=new ArrayList<>();
		try {
			SysResult sysResult = objectMapper.readValue(jsonData, SysResult.class);
			cartList=(List<Cart>)sysResult.getData();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return cartList;
	}

	
	
	@Override
	public void saveCart(Cart cart) {
		String url="http://cat.jt.com/cart/save";
		Map<String, String > params=new HashMap<>();
		params.put("userId", cart.getUserId()+"");
		params.put("itemId", cart.getItemId()+"");
		params.put("itemTitle", cart.getItemTitle());
		params.put("itemImage", cart.getItemImage());
		params.put("itemPrice", cart.getItemPrice()+"");
		params.put("num", cart.getNum()+"");
		httpClient.doPost(url, params);
	}



	@Override
	public void update(Long userId,Long itemId, Integer num) {
		String url="http://cat.jt.com/cart/update/num/"+userId+"/"+itemId+"/"+num;
		httpClient.doPost(url);
		
		
		
	}



	@Override
	public void deleteCart(Long userId, Long itemId) {
		String url="http://cat.jt.com/cart/delete/"+userId+"/"+itemId;
		httpClient.doGet(url);
	}

}
