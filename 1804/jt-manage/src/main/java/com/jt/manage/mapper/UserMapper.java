package com.jt.manage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.jt.manage.pojo.User;

public interface UserMapper {
	//定义接口方法实现数据查询
	@Select("select * from user")
	/*@Insert()
	@Update()
	@Delete()*/
	List<User> userList();
}
