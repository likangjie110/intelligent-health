package com.cjl.dao;

import com.cjl.pojo.CheckItem;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: 等星星溺水
 * @Date: 2022/10/14 10:50
 * @Description:
 */

/**
 * 持久层Dao接口
 */
public interface CheckItemDao {
    public void add(CheckItem checkItem);
    public Page<CheckItem> selectByCondition(String queryString);
    public void deleteById(Integer id);
    public long findCountByCheckItemId(Integer checkItemId);

    void edit(CheckItem checkItem);

    CheckItem findById(Integer id);

    List<CheckItem> findAll();
}
