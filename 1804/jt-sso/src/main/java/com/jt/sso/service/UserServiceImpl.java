package com.jt.sso.service;

import java.util.Date;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.sso.mapper.UserMapper;
import com.jt.sso.pojo.User;

import redis.clients.jedis.JedisCluster;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private JedisCluster jedisCluster;
	private static ObjectMapper objectMapper=new ObjectMapper();
	/**
	 * 1.根据type类型的值转化为具体的字段
	 * 2.根据用户提供的信息count求和 如果>=1 或者==0 判断用户信息是否存在
	 */
	@Override
	public boolean findCheckUser(String param, Integer type) {
		
		String cloumn=null;
		switch (type) {
		case 1:
			cloumn="username";
			break;
		case 2:
			cloumn="phone";
			break;
		case 3:
			cloumn="email";
			break;
		}
		
		//获取数据库查询数据
		int count=userMapper.findCheckUser(param,cloumn);
		
		return count==0?false:true;
	}
	
	
	@Override
	public void saveUser(User user) {
		//准备后台数据
		String md5pass=new DigestUtils().md5Hex(user.getPassword());
		user.setPassword(md5pass);
		user.setEmail(user.getPhone());
		user.setCreated(new Date());
		user.setUpdated(user.getCreated());
		userMapper.insert(user);
		
		
	}


	@Override
	public String findUserByUP(String username, String password) {
		User userTemp=new User();
		userTemp.setUsername(username);
		userTemp.setPassword(DigestUtils.md5Hex(password));
		User user=userMapper.findUserByUP(userTemp);
		
		if(user==null){
			//如果用户查询结果为空，则表示用户名和密码不正确
			throw new RuntimeException();
		}
		String token=null;
		//如果数据不为空应该生成密钥，转换为json数据
		try {
			user.setPassword(null);//敏感的数据不应该保存在第三方
			String userJson=objectMapper.writeValueAsString(user);
			
			//定义token密钥
			String ps="JT_TICKET"+System.currentTimeMillis()+user.getUsername();
			token=DigestUtils.md5Hex(ps);
			//生成的token保存到redis缓存中
			
			jedisCluster.setex(token, 5*24*3600, userJson);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return token;
	}
}
