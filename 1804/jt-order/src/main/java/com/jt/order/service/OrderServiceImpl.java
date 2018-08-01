package com.jt.order.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.order.mapper.OrderItemMapper;
import com.jt.order.mapper.OrderMapper;
import com.jt.order.mapper.OrderShippingMapper;
import com.jt.order.pojo.Order;
import com.jt.order.pojo.OrderItem;
import com.jt.order.pojo.OrderShipping;

@Service
public class OrderServiceImpl implements OrderService{

	//保存订单信息时，需要关联多张表进行操作
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderItemMapper orderItemMapper;
	@Autowired
	private OrderShippingMapper orderShippingMapper;
	@Override
	public String saveOrder(Order order) {
		
		String orderId=order.getUserId()+""+System.currentTimeMillis();
		Date date =new Date();
		//1.实现订单入库
		order.setOrderId(orderId);
		order.setCreated(date);
		order.setUpdated(date);
		order.setStatus(1);
		orderMapper.insert(order);
		System.out.println("订单信息入库成功");
		
		//2.实现订单物流信息入库
		OrderShipping shipping = order.getOrderShipping();
		shipping.setOrderId(orderId);
		shipping.setCreated(date);
		shipping.setUpdated(date);
		orderShippingMapper.insert(shipping);
		System.out.println("订单物流信息入库成功");
		
		//3.实现订单商品入库
		List<OrderItem> orderItems = order.getOrderItems();
		for (OrderItem orderItem : orderItems) {
			orderItem.setOrderId(orderId);
			orderItem.setCreated(date);
			orderItem.setUpdated(date);
			orderItemMapper.insert(orderItem);
		}
		System.out.println("代码写完了");
		return orderId;
	}
	
	//进行订单查询
	@Override
	public Order findOrderById(Long orderId) {
		
		return orderMapper.findOrderById(orderId);
		
	}

	
	



	
}
