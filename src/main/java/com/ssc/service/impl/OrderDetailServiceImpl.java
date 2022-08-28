package com.ssc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ssc.entity.OrderDetail;
import com.ssc.mapper.OrderDetailMapper;
import com.ssc.service.OrderDetailService;
import org.springframework.stereotype.Service;

/**
 * @ClassName OrderDetailServiceImpl
 * @Authoc 孙少聪
 * @Date 2022/8/28 21:04:35
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
