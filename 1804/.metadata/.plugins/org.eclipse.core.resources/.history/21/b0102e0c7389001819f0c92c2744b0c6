package com.jt.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.SysResult;
import com.jt.web.pojo.Cart;
import com.jt.web.service.CartService;
import com.jt.web.thread.UserThreadLocal;

@Controller
@RequestMapping("/cart/")
public class CartController {
	@Autowired
	private CartService cartService;

	// 购物车新增
	@RequestMapping("add/{itemId}")
	public String saveCart(@PathVariable Long itemId, Cart cart) {
		Long userId = UserThreadLocal.getUser().getId();
		cart.setItemId(itemId);
		cart.setUserId(userId);

		cartService.saveCart(cart);
		return "redirect:/cart/show.html";

	}

	// 购物车展现
	@RequestMapping("/show")
	public String show(Model model) {
		// 根据用户id查找查询购物车数据
		Long userId = UserThreadLocal.getUser().getId();
		List<Cart> cartList = cartService.findCartListByUserId(userId);
		model.addAttribute("cartList", cartList);
		return "cart";
	}

	// 实现购物车中数量更新update/num/1474391961/9
	@RequestMapping("update/num/{itemId}/{num}")
	@ResponseBody
	public SysResult update(@PathVariable Long itemId, @PathVariable Integer num) {
		try {
			Long userId = UserThreadLocal.getUser().getId();
			cartService.update(userId, itemId, num);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "数据更新失败");
	}

	// 实现购物车商品删除http://www.jt.com/cart/delete/1474391961.html
	@RequestMapping("delete/{itemId}")

	public String deleteCart(@PathVariable Long itemId) {
		Long userId = 7L;
		cartService.deleteCart(userId, itemId);
		return "redirect:/cart/show.html";
	}
}
