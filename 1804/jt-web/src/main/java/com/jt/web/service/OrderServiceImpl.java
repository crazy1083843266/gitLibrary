package com.jt.web.service;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.Item;
import com.jt.web.pojo.Order;

@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	private HttpClientService httpClient;
	private static ObjectMapper objectMapper=new ObjectMapper();
	
	@Override
	public String saveOrder(Order order) {
		String url = "http://order.jt.com/order/create";
		String orderId = null;
		//将参数转化为JSON串提交
		try {
			String orderJSON  = objectMapper.writeValueAsString(order);
			Map<String,String> params = new HashMap<String,String>();
			params.put("orderJSON", orderJSON);
			String jsonData = httpClient.doPost(url, params);
			if(StringUtils.isEmpty(jsonData)){
				throw new RuntimeException();
			}
			SysResult sysResult =  objectMapper.readValue(jsonData,SysResult.class);
				if(sysResult.getStatus() == 200){
					orderId = (String) sysResult.getData();
				}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return orderId;
	
	}

	

	@Override
	public Order findOrderById(String id) {
		String url="http://order.jt.com/order/query/"+id;
		String jsonData = httpClient.doGet(url);
		Order order=null;
		try {
			order = objectMapper.readValue(jsonData, Order.class);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		return order;
	}

}
