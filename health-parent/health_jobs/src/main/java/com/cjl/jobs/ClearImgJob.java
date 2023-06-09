package com.cjl.jobs;

import com.cjl.constant.RedisConstant;
import com.cjl.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * @Auther: 等星星溺水
 * @Date: 2022/10/17 11:17
 * @Description:
 */

public class ClearImgJob {
        @Autowired
        private JedisPool jedisPool;
        public void clearImg(){
            //根据Redis中保存的两个set集合进行差值计算，获得垃圾图片名称集合
            Set<String> set =
                    jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES,
                            RedisConstant.SETMEAL_PIC_DB_RESOURCES);
            if(set != null){
                for (String picName : set) {
                    //删除七牛云服务器上的图片
                    QiniuUtils.deleteFileFromQiniu(picName);
                    //从Redis集合中删除图片名称
                    jedisPool.getResource().
                            srem(RedisConstant.SETMEAL_PIC_RESOURCES,picName);
                    System.out.println("自定义清理垃圾图片成功"+picName);
                }
            }
        }
}
