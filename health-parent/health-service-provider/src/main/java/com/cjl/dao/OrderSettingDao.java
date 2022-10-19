package com.cjl.dao;

import com.cjl.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Auther: 等星星溺水
 * @Date: 2022/10/19 13:42
 * @Description:
 */

public interface OrderSettingDao {
    public void add(OrderSetting orderSetting);
    public void editNumberByOrderDate(OrderSetting orderSetting);
    public long findCountByOrderDate(Date orderDate);

    public List<OrderSetting> getOrderSettingByMonth(Map map);

   public void editNumberByDate(OrderSetting orderSetting);
}
