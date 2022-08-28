package com.ssc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ssc.entity.Orders;

/**
 * @ClassName OrderService
 * @Authoc 孙少聪
 * @Date 2022/8/28 21:01:47
 */

public interface OrderService extends IService<Orders> {
    public void submit(Orders orders);
}
