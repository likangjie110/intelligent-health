package com.cjl.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cjl.constant.MessageConstant;
import com.cjl.constant.RedisConstant;
import com.cjl.entity.PageResult;
import com.cjl.entity.QueryPageBean;
import com.cjl.entity.Result;
import com.cjl.pojo.Setmeal;
import com.cjl.service.SetmealService;
import com.cjl.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.util.UUID;

/**
 * @Auther: 等星星溺水
 * @Date: 2022/10/16 15:22
 * @Description:
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Autowired
    private JedisPool jedisPool;

    @Reference
    private SetmealService setmealService;

    //图片上传
    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile){
        try{
            //获取原始文件名
            String originalFilename = imgFile.getOriginalFilename();
            int lastIndexOf = 0;
            if (originalFilename != null) {
                lastIndexOf = originalFilename.lastIndexOf(".");
            }
            //获取文件后缀
            String suffix = null;
            if (originalFilename != null) {
                suffix = originalFilename.substring(lastIndexOf - 1);
            }
            //使用UUID随机产生文件名称，防止同名文件覆盖
            String fileName = UUID.randomUUID().toString() + suffix;
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);
            //图片上传成功
            Result result = new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS);
            result.setData(fileName);
            //将上传图片名称存入Redis，基于Redis的Set集合存储
            //将添加到七牛云里的图片名放入redis的集合中
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            //图片上传失败
            return new Result(false,MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    //新增套餐
    @RequestMapping("add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds){

        try {
            setmealService.add(setmeal,checkgroupIds);

        } catch (Exception e) {
            e.printStackTrace();
           return new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }
        return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    //分页查询
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return   setmealService.findquery(queryPageBean);
    }
}
