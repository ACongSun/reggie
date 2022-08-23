package com.ssc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ssc.entity.DishFlavor;
import com.ssc.mapper.DishFlavorMapper;
import com.ssc.service.DishFlavorService;
import org.springframework.stereotype.Service;

/**
 * @ClassName DishFlavorServiceImpl
 * @Authoc 孙少聪
 * @Date 2022/8/23 08:59:41
 */

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
