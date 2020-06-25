package com.layui.mapper;

import com.layui.entity.BaseModel;

import java.util.List;

/**
 * Created by DATA on 2018/11/18.
 */
public interface BaseDao<T extends BaseModel,PK> {
    int deleteByKey(PK id);
    int insert(T entity);
    int insertSelective(T entity);
    T findByKey(PK id);
    int updateByKey(T entity);
    int updateByKeySelective(T entity);
    List<T> findList(T entity);
}
