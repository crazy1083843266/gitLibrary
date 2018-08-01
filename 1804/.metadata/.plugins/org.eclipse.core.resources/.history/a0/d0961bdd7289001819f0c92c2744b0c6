package com.jt.web.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.User;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private HttpClientService httpClient;
	private static ObjectMapper objectMapper=new ObjectMapper();
	@Override
	
	public void saveUser(User user) {
		String url="http://sso.jt.com/user/doRegister";
		Map<String , String> params=new HashMap<>();
		params.put("username", user.getUsername());
		params.put("password", user.getPassword());
		params.put("phone", user.getPhone());
		params.put("email", user.getEmail());
		String jsonData = httpClient.doPost(url, params);
		
		//判断返回的数据是否正确，不正确则抛出异常
		try {
			SysResult sysResult = objectMapper.readValue(jsonData, SysResult.class);
			if(sysResult.getStatus()!=200){
				throw new RuntimeException();
			}
		} catch (Exception e) {
			e.getMessage();
			throw new RuntimeException();
		}
		
	}
	@Override
	public String findUserByUP(String username, String password) {
		String url="http://sso.jt.com/user/login";
		
		//封装数据
		Map<String , String> params=new HashMap<>();
		params.put("username", username);
		params.put("password", password);
		
		//发起请求
		String jsonData = httpClient.doPost(url, params);
		//将Json转换成对象
		try {
			SysResult result = objectMapper.readValue(jsonData, SysResult.class);
			
			if(result.getStatus()==200){
				String token=(String)result.getData();
				return token;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		return null;
	}
	
	
}
