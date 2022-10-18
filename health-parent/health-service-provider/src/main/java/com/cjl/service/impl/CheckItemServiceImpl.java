package com.cjl.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cjl.dao.CheckItemDao;
import com.cjl.entity.PageResult;
import com.cjl.entity.QueryPageBean;
import com.cjl.entity.Result;
import com.cjl.pojo.CheckItem;
import com.cjl.service.CheckItemService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 检查项服务
 */
@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService {
    @Autowired
    private CheckItemDao checkItemDao;
    //新增
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page<CheckItem> page = checkItemDao.selectByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    public void delete(Integer id) throws RuntimeException{
        //查询当前检查项是否和检查组关联
        long count = checkItemDao.findCountByCheckItemId(id);
        if(count > 0){
            //当前检查项被引用，不能删除
            throw new RuntimeException("当前检查项被引用，不能删除");
        }
        checkItemDao.deleteById(id);
    }

    public void edit(CheckItem checkItem) {
        //调用dao层的编辑方法，
        checkItemDao.edit(checkItem);
    }


    public CheckItem findById(Integer id) {
        return checkItemDao.findById(id);
    }

    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }
}
