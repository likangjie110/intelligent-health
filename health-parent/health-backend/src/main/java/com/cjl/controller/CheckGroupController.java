package com.cjl.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cjl.constant.MessageConstant;
import com.cjl.entity.PageResult;
import com.cjl.entity.QueryPageBean;
import com.cjl.entity.Result;
import com.cjl.pojo.CheckGroup;
import com.cjl.service.CheckGroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: 等星星溺水
 * @Date: 2022/10/15 15:05
 * @Description:
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {

    @Reference
    private CheckGroupService checkGroupService;

    //新增
    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){
        try {
            checkGroupService.add(checkGroup,checkitemIds);
        }catch (Exception e){
            //新增失败
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
        //新增成功
        return new Result(true,MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }
    //分页查询
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
     return   checkGroupService.findquery(queryPageBean);
     }
     //根据前端的id,查询所对应的检查组对象,checkgroup/findById.do?id=" + row.id
     @RequestMapping("/findById")
     public Result findById(Integer id){
         try {
             CheckGroup checkGroup = checkGroupService.findByid(id);
             return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkGroup);
         } catch (Exception e) {
             e.printStackTrace();
             return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
         }
     }
     //根据检擦组的id，在检查组与检查项的表中查出对应的检查项的id,List<Integer>
     @RequestMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(Integer id){
         try {
             List<Integer> checkitemIds = checkGroupService.findCheckItemIdsByCheckGroupId(id);
             return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkitemIds);
         } catch (Exception e) {
             e.printStackTrace();
             return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
         }

     }

     //根据编辑表单中提交的的数据，进行数据库内容的修改
    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){
        try {
            checkGroupService.edit(checkGroup,checkitemIds);
        }catch (Exception e){//更新失败
            return new Result(false, MessageConstant.EDIT_CHECKGROUP_FAIL);
        }//更新成功
        return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }
    //查询所有的检查组信息
    @RequestMapping("/findAll")
    public Result findAll(){
        List<CheckGroup> checkGroupList = checkGroupService.findAll();
        if(checkGroupList != null && checkGroupList.size() > 0){
            Result result = new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS);
            result.setData(checkGroupList);
            return result;
        }
        return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
    }
}
