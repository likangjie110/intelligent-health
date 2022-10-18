package com.cjl.service;

import com.cjl.entity.PageResult;
import com.cjl.entity.QueryPageBean;
import com.cjl.entity.Result;
import com.cjl.pojo.CheckItem;

import java.util.List;

/**
 * @Auther: 等星星溺水
 * @Date: 2022/10/14 10:36
 * @Description:
 */
//服务接口
/**
 * 检查项服务接口
 */
public interface CheckItemService {
    public void add(CheckItem checkItem);

    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

    public void delete(Integer id);

    void edit(CheckItem checkItem);

    CheckItem findById(Integer id);

   List<CheckItem> findAll();
}
