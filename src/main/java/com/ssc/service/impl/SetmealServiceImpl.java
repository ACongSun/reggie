package com.ssc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ssc.dto.SetmealDto;
import com.ssc.entity.Category;
import com.ssc.entity.Dish;
import com.ssc.entity.Setmeal;
import com.ssc.entity.SetmealDish;
import com.ssc.mapper.SetmealMapper;
import com.ssc.service.CategoryService;
import com.ssc.service.SetmealDishService;
import com.ssc.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName SetmealServiceImpl
 * @Authoc 孙少聪
 * @Date 2022/8/22 10:51:23
 */
@Slf4j
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 根据id获取套餐和菜品信息
     * @param id
     * @return
     */
    @Override
    public SetmealDto getSetmealWithDish(Long id) {

        Setmeal setmeal = this.getById(id);

        SetmealDto setmealDto = new SetmealDto();
        BeanUtils.copyProperties(setmeal, setmealDto);

        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId, setmeal.getId());
        List<SetmealDish> list = setmealDishService.list(queryWrapper);

        Category category = categoryService.getById(setmeal.getCategoryId());

        setmealDto.setCategoryName(category.getName());
        setmealDto.setSetmealDishes(list);

        return setmealDto;
    }


    /**
     * 修改套餐信息
     * @param setmealDto
     */
    @Override
    public void updateSetmealWithDish(SetmealDto setmealDto) {
        log.info("信息:{}", setmealDto);
        this.updateById(setmealDto);

        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId, setmealDto.getId());
        setmealDishService.remove(queryWrapper);

        List<SetmealDish> list = setmealDto.getSetmealDishes();
        list = list.stream().map(item -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        setmealDishService.saveBatch(list);
    }
}
