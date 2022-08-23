package com.ssc.dto;


import com.ssc.entity.Setmeal;
import com.ssc.entity.SetmealDish;
import lombok.Data;

import java.util.List;


@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
