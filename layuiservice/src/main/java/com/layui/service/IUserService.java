package com.layui.service;

import java.util.List;
import java.util.Map;

import com.layui.entity.User;

public interface IUserService {
	List<User> getFindList(User user);
	
	List<Map<String,Object>> getExcelData();
}
