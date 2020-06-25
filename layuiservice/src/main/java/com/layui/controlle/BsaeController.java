package com.layui.controlle;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.layui.entity.BaseModel;
import com.layui.entity.User;
import com.layui.service.SuperService;
import com.layui.util.ResultStatus;
import com.layui.util.ResultUtil;

/**
 * Created by DATA on 2018/11/19.
 */
public abstract class BsaeController<T extends BaseModel,PK> {
    public BsaeController(){}

    protected abstract SuperService<T,PK> getService();
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Map<String, Object> delete(@RequestBody T entity){
        this.getService().deleteByKey(entity.getId());
        return ResultUtil.combinationResult(ResultStatus.SUCCESS, null);
    }
    @RequestMapping(value = "/add")
    @ResponseBody
    public Map<String, Object> add(@RequestBody T entity){
        this.getService().insertSelective(entity);
        return ResultUtil.combinationResult(ResultStatus.SUCCESS, entity);
    }
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(@RequestBody T entity){
        this.getService().updateByKeySelective(entity);
        return ResultUtil.combinationResult(ResultStatus.SUCCESS, entity);
    }

    @RequestMapping(value = "/findByKey")
    @ResponseBody
    public Map<String, Object> findByKey(PK id){
       Object t= this.getService().findByKey(id);
        return ResultUtil.combinationResult(ResultStatus.SUCCESS, t);
    }

    @RequestMapping(value = "/find")
    @ResponseBody
    public Map<String, Object> find(@RequestBody T entity){
        List<T> list=this.getService().findList(entity);
        PageInfo<T> page=new PageInfo<T>(list);
        page.setList(null);
        return ResultUtil.combinationRows(ResultStatus.SUCCESS, list,page);
    }



}
