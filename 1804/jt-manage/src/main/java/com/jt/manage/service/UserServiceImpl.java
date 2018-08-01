package com.jt.manage.service;

import java.util.List;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jt.manage.mapper.UserMapper;
import com.jt.manage.pojo.User;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;  //代理对象

	@Override
	//@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public List<User> userList() {
		
		return userMapper.userList();
	}
}
