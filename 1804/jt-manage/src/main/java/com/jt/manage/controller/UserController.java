package com.jt.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jt.manage.pojo.User;
import com.jt.manage.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/findAll")
	public String findUserList(Model model){
		
		List<User> userList = userService.userList();
		model.addAttribute("userList", userList);
		return "userList";
	}
	
	/*String view = "userList";
	ModelAndView andView = new ModelAndView();
	andView.setViewName(view);
	andView.addObject("userList", userList);
	return andView;*/
	
	
	
	
}
