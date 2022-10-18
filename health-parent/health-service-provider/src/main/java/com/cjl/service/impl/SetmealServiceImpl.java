package com.cjl.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cjl.constant.RedisConstant;
import com.cjl.dao.SetmealDao;
import com.cjl.entity.PageResult;
import com.cjl.entity.QueryPageBean;
import com.cjl.pojo.CheckItem;
import com.cjl.pojo.Setmeal;
import com.cjl.service.SetmealService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qiniu.storage.ApiUploadV1MakeBlock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: 等星星溺水
 * @Date: 2022/10/16 15:43
 * @Description:
 */
@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealDao setmealDao;

    @Autowired
    private JedisPool jedisPool;

    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        //拿到新增套餐的数据，有两部分呢，基础信息，以及检查组的信息
        //处理基本信息
        setmealDao.add(setmeal);
        if(checkgroupIds != null && checkgroupIds.length > 0){
            //绑定套餐和检查组的多对多关系
            //处理检查组的ids,传入插入的套餐id,在套餐与检查组表中进行数据的绑定
            setSetmealAndCheckGroup(setmeal.getId(),checkgroupIds);
        }
        //将添加到数据库的图片名放入redis的集合中
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, setmeal.getImg());
    }

    public PageResult findquery(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<CheckItem> page = setmealDao.selectByCondition(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }

    public void setSetmealAndCheckGroup(Integer id,Integer[] checkgroupIds){
        //创建一个Map集合将套餐的id，和检查组的id存入
        for (Integer checkgroupId : checkgroupIds) {
            Map<String,Integer> map = new HashMap<>();
            map.put("setmeal_id",id);
            map.put("checkgroup_id",checkgroupId);
            setmealDao.setSetmealAndCheckGroup(map);
        }
    }
}
