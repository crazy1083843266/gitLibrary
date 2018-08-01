package com.jt.order.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.jt.dubbo.pojo.Order;
import com.jt.dubbo.pojo.OrderItem;
import com.jt.dubbo.pojo.OrderShipping;
import com.jt.order.mapper.OrderItemMapper;
import com.jt.order.mapper.OrderMapper;
import com.jt.order.mapper.OrderShippingMapper;

public class RabbitOrderServiceImpl {

	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderItemMapper orderItemMapper;
	@Autowired
	private OrderShippingMapper orderShippingMapper;

	public void saveOrder(Order order) {
		
		String orderId=order.getOrderId();
		Date date =new Date();
		// 1.实现订单入库
		order.setOrderId(orderId);
		order.setCreated(date);
		order.setUpdated(date);
		order.setStatus(1);
		orderMapper.insert(order);
		System.out.println("订单信息入库成功");

		// 2.实现订单物流信息入库
		OrderShipping shipping = order.getOrderShipping();
		shipping.setOrderId(orderId);
		shipping.setCreated(date);
		shipping.setUpdated(date);
		orderShippingMapper.insert(shipping);
		System.out.println("订单物流信息入库成功");

		// 3.实现订单商品入库
		List<OrderItem> orderItems = order.getOrderItems();
		for (OrderItem orderItem : orderItems) {
			orderItem.setOrderId(orderId);
			orderItem.setCreated(date);
			orderItem.setUpdated(date);
			orderItemMapper.insert(orderItem);
		}
		System.out.println("消息队列数据处理完成");

	}
}
