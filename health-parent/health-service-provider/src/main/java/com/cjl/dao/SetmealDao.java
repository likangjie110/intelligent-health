package com.cjl.dao;

import com.cjl.pojo.CheckItem;
import com.cjl.pojo.Setmeal;
import com.github.pagehelper.Page;

import java.util.Map;

/**
 * @Auther: 等星星溺水
 * @Date: 2022/10/16 15:50
 * @Description:
 */

public interface SetmealDao {
    public void add(Setmeal setmeal);
    public void setSetmealAndCheckGroup(Map<String, Integer> map);

    Page<CheckItem> selectByCondition(String queryString);
}
