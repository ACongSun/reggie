package com.ssc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ssc.entity.Dish;
import com.ssc.mapper.DishMapper;
import com.ssc.service.DishService;
import org.springframework.stereotype.Service;

/**
 * @ClassName DishServiceImpl
 * @Authoc 孙少聪
 * @Date 2022/8/22 10:48:42
 */

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
}
