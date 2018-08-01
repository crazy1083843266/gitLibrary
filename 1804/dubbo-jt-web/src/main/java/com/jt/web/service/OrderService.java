package com.jt.web.service;

import java.util.List;

import com.jt.dubbo.pojo.Cart;
import com.jt.web.pojo.Order;

public interface OrderService {

	String saveOrder(Order order);

	

	Order findOrderById(String id);



	



	
}
