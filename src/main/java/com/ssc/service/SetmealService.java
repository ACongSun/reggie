package com.ssc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ssc.dto.SetmealDto;
import com.ssc.entity.Setmeal;

public interface SetmealService extends IService<Setmeal> {

    public SetmealDto getSetmealWithDish(Long id);

    public void updateSetmealWithDish(SetmealDto setmealDto);
}
