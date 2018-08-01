package com.jt.order.mapper;

import java.util.Date;
import java.util.List;

import com.jt.common.mapper.SysMapper;
import com.jt.dubbo.pojo.Cart;
import com.jt.dubbo.pojo.Order;


public interface OrderMapper extends SysMapper<Order>{

	Order findOrderById(String orderId);

	void updateStatus(Date timeOut);

	List<Cart> findCartListById(Long userId);
    
}