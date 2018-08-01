package com.jt.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	
	@RequestMapping("/index")
	public String index(){
		
		return "index";
	}
	
	
	/**
	 * url:/page/item-add
	 * restFul语法规则
	 * 1.传递的参数需要使用"/"分割
	 * 2.传递的参数需要使用{}包裹
	 * 接收参数要求
	 * 1.接收的参数必须是经过{}包裹的参数
	 * 2.需要使用注解实现数据转化
	 * 
	 * get请求:
	 * 	localhost:8091/page?moduleName='item-add'
	 * restFul请求
	 * 	localhost:8091/page/item-add
	 * 
	 * 注意事项:
	 * 	@PathVariable(value="module")
	 *  如果参数名称接收是一致的可以省略不写value属性.
	 *  如果不一致,则必须添加value
	 */
	@RequestMapping("/page/{module}")
	public String Module(@PathVariable String module){
		
		return module;
	}
	
	
	
	
	
	
	
	
	
	
	

}
