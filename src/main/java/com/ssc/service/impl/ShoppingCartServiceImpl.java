package com.ssc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ssc.entity.ShoppingCart;
import com.ssc.mapper.ShoppingCartMapper;
import com.ssc.service.ShoppingCartService;
import org.springframework.stereotype.Service;

/**
 * @ClassName ShoppingCartServiceImpl
 * @Authoc 孙少聪
 * @Date 2022/8/25 16:41:44
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}
