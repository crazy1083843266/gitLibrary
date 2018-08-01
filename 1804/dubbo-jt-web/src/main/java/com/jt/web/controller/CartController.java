package com.jt.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.SysResult;
import com.jt.dubbo.pojo.Cart;
import com.jt.dubbo.service.DubboCartService;
import com.jt.web.thread.UserThreadLocal;

@Controller
@RequestMapping("/cart")
public class CartController {
	@Autowired
	private DubboCartService cartService;
	

	// 消费者
	@RequestMapping("/show")
	public String showCart(Model model) {

		Long userId = UserThreadLocal.getUser().getId();
		List<Cart> cartList = cartService.findCartByUserId(userId);
		model.addAttribute("cartList", cartList);
		return "cart";

	}
	
	
	@RequestMapping("/add/{itemId}")
	public String saveCart(@PathVariable Long itemId,Cart cart){
		Long userId=UserThreadLocal.getUser().getId();
		cart.setItemId(itemId);
		cart.setUserId(userId);
		cartService.saveCart(cart);
		return "redirect:/cart/show.html";
	}
	
	
	
	//  www.jt.com/service/cart/update/num/1474391935/9
	@RequestMapping("/update/num/{itemId}/{num}")
	@ResponseBody
	public SysResult updateCartNum(@PathVariable Long itemId,@PathVariable Integer num){
		Long userId=UserThreadLocal.getUser().getId();
		try {
			cartService.updateCartNum(userId,itemId,num);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "修改商品失败");
		
	}
	
	
	// http://www.jt.com/cart/delete/1474391935.html
	@RequestMapping("/delete/{itemId}")
	public String deleteCart(@PathVariable Long itemId){
		Long userId = UserThreadLocal.getUser().getId();
		Cart cart = new Cart();
		cart.setItemId(itemId);
		cart.setUserId(userId);
		cartService.deleteCart(cart);
		return "redirect:/cart/show.html";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
