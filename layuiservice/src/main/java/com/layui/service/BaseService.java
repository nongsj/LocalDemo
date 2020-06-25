package com.layui.service;



import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.github.pagehelper.PageHelper;
import com.layui.entity.BaseModel;
import com.layui.entity.User;
import com.layui.mapper.BaseDao;

import java.util.List;
import java.util.UUID;

/**
 * Created by DATA on 2018/11/18.
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Transactional
public abstract class BaseService <T extends BaseModel,PK,E extends BaseDao> implements SuperService<T,PK>{
    public BaseService(){}
    protected  abstract E getDao();


    public int deleteByKey(Object id){
        return this.getDao().deleteByKey(id);
    }

    public int insert(T entity){
        Assert.notNull(entity,"实体类为空");
        entity.setId(UUID.randomUUID().toString());
        return this.getDao().insert(entity);
    }

    public int insertSelective(T entity){
        Assert.notNull(entity,"实体类为空");
        entity.setId(UUID.randomUUID().toString());
        return this.getDao().insertSelective(entity);
    }



    public T findByKey(PK id){
        return (T) this.getDao().findByKey(id);
    }


    public T updateByKey(T entity){
        Assert.notNull(entity,"实体类为空");
        this.getDao().updateByKey(entity);
        return entity;
    }


    public T updateByKeySelective(T entity){
        Assert.notNull(entity,"实体类为空");
        this.getDao().updateByKeySelective(entity);
        return entity;
    }


    public List<T> findList(T entity){
        if(entity.getPageNumber()!=null && entity.getPageNumber()>0 && entity.getPageSize()!=null && entity.getPageSize()>0){
            PageHelper.startPage(entity.getPageNumber().intValue(),entity.getPageSize().intValue());
        }
        if(entity.getSortName()!=null && entity.getSortOrder()!=null){
            String sortName=entity.getSortName();
            sortName.replace(","," "+entity.getSortOrder()+",");
            sortName=sortName+" "+entity.getSortOrder();
           PageHelper.orderBy(sortName);
        }
        return this.getDao().findList(entity);
    }
}
