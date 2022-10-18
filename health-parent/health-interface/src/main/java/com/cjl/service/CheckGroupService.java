package com.cjl.service;

import com.cjl.entity.PageResult;
import com.cjl.entity.QueryPageBean;
import com.cjl.pojo.CheckGroup;

import java.util.List;

/**
 * @Auther: 等星星溺水
 * @Date: 2022/10/15 15:12
 * @Description:
 */

public interface CheckGroupService {
    void add(CheckGroup checkGroup, Integer[] checkitemIds);

    PageResult findquery(QueryPageBean queryPageBean);

    CheckGroup findByid(Integer id);
    //根据检擦组的id，在检查组与检查项的表中查出对应的检查项的id,List<Integer>
    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    void edit(CheckGroup checkGroup, Integer[] checkitemIds);

    List<CheckGroup> findAll();
}
