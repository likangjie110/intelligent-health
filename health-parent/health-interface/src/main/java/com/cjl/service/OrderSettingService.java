package com.cjl.service;

import com.cjl.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * @Auther: 等星星溺水
 * @Date: 2022/10/19 13:34
 * @Description:
 */

public interface OrderSettingService {

    public void add(List<OrderSetting> list);

   public List<Map> getOrderSettingByMonth(String date);

   public void editNumberByDate(OrderSetting orderSetting);
}
