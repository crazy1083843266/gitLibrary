package com.jt.sso.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.SysResult;
import com.jt.sso.pojo.User;
import com.jt.sso.service.UserService;

import redis.clients.jedis.JedisCluster;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private JedisCluster jedisCluster;
	//实现用户的校验
	@RequestMapping("/check/{param}/{type}")
	@ResponseBody
	public MappingJacksonValue checkUser(
			@PathVariable String param,
			@PathVariable Integer type,String callback){
		
		boolean flag=userService.findCheckUser(param,type);
		MappingJacksonValue jacksonValue=new MappingJacksonValue(SysResult.oK(flag));
		jacksonValue.setJsonpFunction(callback);
		return jacksonValue;
	}
	
	@RequestMapping("/doRegister")
	@ResponseBody
	public SysResult saveUser(User user){
		try {
			userService.saveUser(user);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "新增用户失败");
	}
	@RequestMapping("/login")
	@ResponseBody
	public SysResult findUserByUP(String username,String password){
		try {
			String token=userService.findUserByUP(username,password);
			
			return SysResult.oK(token);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "用户查询失败");
	}
	
	//登入成功后回显数据
	@RequestMapping("/query/{ticket}")
	@ResponseBody
	public MappingJacksonValue findUserByToken(@PathVariable String ticket,String callback){
		String userJson=jedisCluster.get(ticket);
		
		MappingJacksonValue jacksonValue=null;
		if(StringUtils.isEmpty(userJson)){
			jacksonValue=new MappingJacksonValue(SysResult.build(201, "用户查询失败"));
			jacksonValue.setJsonpFunction(callback);
			return jacksonValue;
		}
		jacksonValue=new MappingJacksonValue(SysResult.oK(userJson));
		jacksonValue.setJsonpFunction(callback);
		return jacksonValue;
	}
	
	
	
	
	
}
