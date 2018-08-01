package com.jt.web.intercept;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.web.pojo.User;
import com.jt.web.thread.UserThreadLocal;

import redis.clients.jedis.JedisCluster;

public class UserInterceptor implements HandlerInterceptor{
	@Autowired
	private JedisCluster jedisCluster;
	
	
	private static ObjectMapper objectMapper=new ObjectMapper();
	/**
	 * 在真正执行业务逻辑之前拦截
	 * 1.return false;表示请求拦截，需要进行重定向另外的页面中
	 * 	return true； 表示放行
	 * 2.用户拦截器业务逻辑
	 * a 获取cookie信息
	 * b 获取ticket值
	 * c 从redis中获取缓存数据
	 * d 将userJson转换为User对象
	 * e 如果用户没有Cookie或者json，那么用户需要重定向到登录页面
	 */
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//1.获取cookie信息
		Cookie[] cookies = request.getCookies();
		String token=null;
		for (Cookie cookie : cookies) {
			if("JT_TICKET".equals(cookie.getName())){
				token=cookie.getValue();
				break;
			}
		}
		//2.从redis中获取缓存数据
		if(!StringUtils.isEmpty(token)){
			String userJson = jedisCluster.get(token);
			if(!StringUtils.isEmpty(userJson)){
				User user = objectMapper.readValue(userJson, User.class);
				//将用户信息存到本地线程中ThreadLocal
				UserThreadLocal.setUser(user);
				//获取用户信息后需要传递给服务器程序
				return true;
			}
		}
		//重定向到登录页面
		response.sendRedirect("/user/login.html");
		return false;
	}

	//在请i求完成后返回用户之前
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		
	}

	//用户展现页面之前
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		UserThreadLocal.remove();
		
	}

}
