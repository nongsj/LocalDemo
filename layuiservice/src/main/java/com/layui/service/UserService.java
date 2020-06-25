package com.layui.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.layui.entity.User;
import com.layui.mapper.UserDao;

@Service
public class UserService extends BaseService<User, String, UserDao> implements IUserService{

	@Autowired
	private UserDao userDao;

	@Override
	protected UserDao getDao() {
		return userDao;
	}

	@Override
	public List<User> getFindList(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> getExcelData() {
		List<Map<String, Object>> data = excelData();
		return data;
	}

	private List<Map<String, Object>> excelData(){
		List<Map<String, Object>> data = userDao.excelData();
		return data;
	}
	
}
