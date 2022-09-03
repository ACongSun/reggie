package com.ssc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ssc.dto.DishDto;
import com.ssc.entity.Dish;

public interface DishService extends IService<Dish> {

    public String saveWithFlavor(DishDto dishDto);


    public DishDto getByIdWithFlavor(Long id);

    public void updateWithFlavor(DishDto dishDto);
}
