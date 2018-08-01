package com.jt.web.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.SysResult;
import com.jt.web.pojo.User;
import com.jt.web.service.UserService;

import redis.clients.jedis.JedisCluster;
@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private JedisCluster JedisCluster;
	@RequestMapping("/{module}")
	public String module(@PathVariable String module){
		return module;
	}
	
	
	@RequestMapping("/doRegister")
	@ResponseBody
	public SysResult savaUser(User user){
		try {
			
			userService.saveUser(user);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"用户新增失败") ;
		
	}
	
	//实现用户登入操作
	@RequestMapping("/doLogin")
	@ResponseBody
	public SysResult findUserByUP(String username,String password,HttpServletResponse response){
		try {
			String token=userService.findUserByUP(username,password);
			//如果数据为空
			if(StringUtils.isEmpty(token)){
				return SysResult.build(201, "用户登入失败");
			}
			
			//如果数据不为空，则将token存到cookie中
			Cookie cookie=new Cookie("JT_TICKET", token);
			//设定cookie声明周期，0表示Cookie立即销毁，-1表示会话结束后销毁
			cookie.setMaxAge(5*24*3600);//设置cookie超时时长，单位为秒
			cookie.setPath("/");//表示cookie的所属路径
			//保存cookie对象
			response.addCookie(cookie);
			
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "用户登入失败");
	}
	
	//用户登入之后退出操作
	/**
	 * 1.先获取cookie信息删除cookie
	 * 2.删除redis中的数据
	 * @return
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request,HttpServletResponse response){
		//1.获取cookie,删除redis中的数据
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if("JT_TICKET".equals(cookie.getName())){
				String token = cookie.getValue();
				JedisCluster.del(token);//删除缓存数据
				//删除原有的cookie
				Cookie cookie2=new Cookie("JT_TICKET","");
				cookie2.setMaxAge(0);
				cookie2.setPath("/");
				response.addCookie(cookie2);
				break;
			}
		}
		return "redirect:/index.html";
	}
	
	
	
	
	
	
	
	
}
