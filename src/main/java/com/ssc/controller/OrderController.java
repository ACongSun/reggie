package com.ssc.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ssc.common.BaseContext;
import com.ssc.common.R;
import com.ssc.entity.OrderDetail;
import com.ssc.entity.Orders;
import com.ssc.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName OrderController
 * @Authoc 孙少聪
 * @Date 2022/8/28 21:05:25
 */

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("submit")
    public R<String> submit(@RequestBody Orders orders){
        orderService.submit(orders);
        return R.success("下单成功！！");
    }

    @GetMapping("/userPage")
    public R<Page> userPage(int page, int pageSize){
        Long userId = BaseContext.getCurrentId();
        Page<Orders> pageInfo = new Page<>(page, pageSize);

        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Orders::getUserId, userId)
                .orderByDesc(Orders::getOrderTime);
        orderService.page(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }
}
