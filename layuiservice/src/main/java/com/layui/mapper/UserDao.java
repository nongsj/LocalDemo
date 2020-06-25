package com.layui.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.layui.entity.User;

@Repository
@org.apache.ibatis.annotations.Mapper
public interface UserDao extends BaseDao<User, String> {
	List<Map<String,Object>> excelData();
}
