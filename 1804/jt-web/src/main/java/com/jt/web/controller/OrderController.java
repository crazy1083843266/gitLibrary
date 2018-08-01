package com.jt.web.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.SysResult;
import com.jt.web.pojo.Cart;
import com.jt.web.pojo.Item;
import com.jt.web.pojo.Order;
import com.jt.web.service.CartService;
import com.jt.web.service.OrderService;
import com.jt.web.thread.UserThreadLocal;

@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private CartService cartService;
	@Autowired
	private OrderService orderService;
	@RequestMapping("/create")
	public String create(Model model){
		
		//查询当前登录用户的购物信息
		Long userId=UserThreadLocal.getUser().getId();
		List<Cart> carts=cartService.findCartListByUserId(userId);
		
		model.addAttribute("carts",carts);
		return "order-cart";
	}
	
	@RequestMapping("/submit")
	@ResponseBody
	public SysResult saveOrder(Order order){
		try {
			Long userId=UserThreadLocal.getUser().getId();
			order.setUserId(userId);
			String orderId=orderService.saveOrder(order);
			if(!StringUtils.isEmpty(orderId)){
				return SysResult.oK(orderId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "订单入库失败");
	}
	
	//实现订单的查询  http://www.jt.com/order/success.html?id=171531557991771
	@RequestMapping("/success")
	public String success(String id,Model model){
		/**
		 * 根据orderId的值查询后台的数据库，订单的全部消息要求查询三张表
		 * 1.利用通用Mapper实现数据查询(中)
		 * 2.自己编辑Sql语句实现数据封装(难)
		 */
		Order order=orderService.findOrderById(id);
		model.addAttribute("order", order);
		return "success";
	}
	
	
	
	
	
	
	
	
}
