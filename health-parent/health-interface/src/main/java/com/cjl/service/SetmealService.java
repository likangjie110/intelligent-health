package com.cjl.service;

import com.cjl.entity.PageResult;
import com.cjl.entity.QueryPageBean;
import com.cjl.pojo.Setmeal;

/**
 * @Auther: 等星星溺水
 * @Date: 2022/10/16 15:42
 * @Description:
 */

public interface SetmealService {

    void add(Setmeal setmeal, Integer[] checkgroupIds);

    PageResult findquery(QueryPageBean queryPageBean);
}
