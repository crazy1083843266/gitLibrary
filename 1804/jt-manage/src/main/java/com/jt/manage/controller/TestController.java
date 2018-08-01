package com.jt.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.manage.mapper.ItemMapper;

@Controller
public class TestController {
	
	@Autowired
	private ItemMapper itemMapper;
	
	@RequestMapping("/testFindCount")
	@ResponseBody
	public int findCount(){
		
		return itemMapper.TestFindCount();
	}
}
