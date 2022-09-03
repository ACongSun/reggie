package com.ssc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ssc.dto.DishDto;
import com.ssc.entity.Dish;
import com.ssc.entity.DishFlavor;
import com.ssc.mapper.DishMapper;
import com.ssc.service.DishFlavorService;
import com.ssc.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName DishServiceImpl
 * @Authoc 孙少聪
 * @Date 2022/8/22 10:48:42
 */

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;

    /**
     * 新增菜品，同时保存口味信息
     * @param dishDto
     * @return
     */
    @Transactional
    @Override
    public String saveWithFlavor(DishDto dishDto) {
        // 保存菜品表
        this.save(dishDto);

        // 菜品id
        Long dishId = dishDto.getId();

        // 菜品口味
        List<DishFlavor> flavors = dishDto.getFlavors();

        // 将dishId保存到flavors的每个口味儿对象中去
        flavors.stream().map(item -> {
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());

        // 批量存入
        dishFlavorService.saveBatch(flavors); // 没有存dishId
        return null;
    }

    /**
     * 根据id查询菜品 和 口味 信息
     * @param id
     * @return
     */
    @Override
    public DishDto getByIdWithFlavor(Long id) {
        // 查询菜品基本信息 从dish表查询
        Dish dish = this.getById(id);
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish, dishDto);


        // 查询当前菜品对应的口味信息，从dish_flavor表中查询
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dish.getId());

        List<DishFlavor> flavors = dishFlavorService.list(queryWrapper);
        dishDto.setFlavors(flavors);

        return dishDto;
    }

    /**
     * 更新菜品表和口味表
     * @param dishDto
     */
    @Override
    public void updateWithFlavor(DishDto dishDto) {

        // 更新dish表基本信息
        this.updateById(dishDto);

        // 清理dish_flavor表
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dishDto.getId());
        dishFlavorService.remove(queryWrapper);

        // 新提交的数据插入进去
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map(item -> {
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);
    }
}
