package com.layui.service;

import org.springframework.transaction.annotation.Transactional;

import com.layui.entity.BaseModel;

import java.util.List;

/**
 * Created by DATA on 2018/11/19.
 */
public interface SuperService<T extends BaseModel,PK>{
    @Transactional
    int deleteByKey(Object id);

    @Transactional
    int insert(T entity);

    @Transactional
    int insertSelective(T entity);


    @Transactional
    T findByKey(PK id);

    @Transactional
    T updateByKey(T entity);

    @Transactional
    T updateByKeySelective(T entity);

    @Transactional
    List<T> findList(T entity);
}
