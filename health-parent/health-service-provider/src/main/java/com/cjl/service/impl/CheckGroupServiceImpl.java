package com.cjl.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cjl.dao.CheckGroupDao;
import com.cjl.entity.PageResult;
import com.cjl.entity.QueryPageBean;
import com.cjl.pojo.CheckGroup;
import com.cjl.service.CheckGroupService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: 等星星溺水
 * @Date: 2022/10/15 15:17
 * @Description:
 */
//dubbo的Service和事务同时用时需要指定引用的类
@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {


    @Autowired
    private CheckGroupDao checkGroupDao;

    //添加检查组合，同时需要设置检查组合和检查项的关联关系
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        checkGroupDao.add(checkGroup);
        //checkGroup.getId()是获取到了检查组的自增id了
        setCheckGroupAndCheckItem(checkGroup.getId(),checkitemIds);
    }


    public PageResult findquery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage,pageSize);//分页查询助手，将当前页以及分页大小传入
        Page<CheckGroup> page=checkGroupDao.selectByCondition(queryString);

        return new PageResult(page.getTotal(),page.getResult());
        //返回总条数，以及查询结果集
    }

    //根据id查询检查组对象
    public CheckGroup findByid(Integer id) {
        return  checkGroupDao.findByid(id);
    }

    //根据检擦组的id，在检查组与检查项的表中查出对应的检查项的id,List<Integer>
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(id);
    }

    //编辑检查组对象以及与检查项的关系表
    public void edit(CheckGroup checkGroup, Integer[] checkitemIds) {
        //更新检查组基本信息
        checkGroupDao.edit(checkGroup);
        //根据检查组id删除中间表数据（清理原有关联关系）
        checkGroupDao.deleteAssociation(checkGroup.getId());
        //向中间表(t_checkgroup_checkitem)插入数据（建立检查组和检查项关联关系）
        setCheckGroupAndCheckItem(checkGroup.getId(),checkitemIds);
    }

   //查询所有的检查组信息
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }

    //设置检查组合和检查项的关联关系
    public void setCheckGroupAndCheckItem(Integer checkGroupId,Integer[] checkitemIds){
        if(checkitemIds != null && checkitemIds.length > 0){
            for (Integer checkitemId : checkitemIds) {
                Map<String,Integer> map = new HashMap<>();
                map.put("checkgroup_id",checkGroupId);
                map.put("checkitem_id",checkitemId);
                checkGroupDao.setCheckGroupAndCheckItem(map);
            }
        }
    }

}
