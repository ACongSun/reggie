package com.ssc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ssc.common.CustomException;
import com.ssc.entity.Category;
import com.ssc.entity.Dish;
import com.ssc.entity.Setmeal;
import com.ssc.mapper.CategoryMapper;
import com.ssc.service.CategoryService;
import com.ssc.service.DishService;
import com.ssc.service.EmployeeService;
import com.ssc.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName CategoryServiceImpl
 * @Authoc 孙少聪
 * @Date 2022/8/22 09:53:22
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    /**
     * 根据ids删除分类，删除之前需要进行判断
     * @param ids
     */
    @Override
    public void remove(Long ids) {
        LambdaQueryWrapper<Dish> dishQueryWrapper = new LambdaQueryWrapper<>();
        dishQueryWrapper.eq(Dish::getCategoryId, ids);
        int count = dishService.count(dishQueryWrapper);

        // 查询是否关联了菜品
        if (count > 0){
            // 抛出异常
            throw new CustomException("当前分类已被关联菜品，不能删除");
        }

        LambdaQueryWrapper<Setmeal> setmealQueryWrapper = new LambdaQueryWrapper<>();
        setmealQueryWrapper.eq(Setmeal::getCategoryId, ids);
        int count1 = setmealService.count(setmealQueryWrapper);
        // 是否关联了套餐
        if (count1 > 0){
            // 抛出异常
            throw new CustomException("当前分类已被关联套餐，不能删除");
        }

        // 正常删除
        super.removeById(ids);
    }
}
