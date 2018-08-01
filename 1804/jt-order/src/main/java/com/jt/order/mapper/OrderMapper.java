package com.jt.order.mapper;

import java.util.Date;

import com.jt.common.mapper.SysMapper;
import com.jt.order.pojo.Order;

public interface OrderMapper extends SysMapper<Order>{

	Order findOrderById(Long orderId);

	void updateStatus(Date timeOut);
    
}