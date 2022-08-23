package com.ssc.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ssc.common.R;
import com.ssc.dto.DishDto;
import com.ssc.entity.Category;
import com.ssc.entity.Dish;
import com.ssc.entity.DishFlavor;
import com.ssc.service.CategoryService;
import com.ssc.service.DishFlavorService;
import com.ssc.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName DishController
 * @Authoc 孙少聪
 * @Date 2022/8/22 10:49:27
 */
@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 分页查询菜品
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){

        Page<Dish> pageInfo = new Page<>(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>(page, pageSize);

        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null, Dish::getName, name)
                    .orderByDesc(Dish::getUpdateTime);
        dishService.page(pageInfo, queryWrapper);

        // 对象拷贝
        BeanUtils.copyProperties(pageInfo, dishDtoPage, "records");

        List<Dish> records = pageInfo.getRecords();

        List<DishDto> list = records.stream().map(item -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);

            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);

            if (category != null){
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }

            return dishDto;
        }).collect(Collectors.toList());

        dishDtoPage.setRecords(list);

        return R.success(dishDtoPage);
    }

    /**
     * 新增菜品和口味
     * @param dishDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){
        dishService.saveWithFlavor(dishDto);
        return R.success("新增菜品成功！");
    }

    /**
     * 根据id查询菜品信息和口味信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id){
        DishDto byIdWithFlavor = dishService.getByIdWithFlavor(id);
        return R.success(byIdWithFlavor);
    }

    /**
     * 修改菜品和口味信息
     * @param dishDto
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto){
        dishService.updateWithFlavor(dishDto);
        return R.success("修改菜品信息成功！");
    }

    /**
     * 菜品批量起售和停售
     * @param ids
     * @return
     */
    @PostMapping("/status/{status}")
    public R<String> status(@PathVariable int status, Long[] ids){
        log.info("修改菜品信息{}，{}",status,ids);
        for (int i = 0; i < ids.length; i++) {
            Dish dish = dishService.getById(ids[i]);
            dish.setStatus(status);
            dishService.updateById(dish);
        }
        return R.success("修改菜品状态成功！");
    }


    /**
     * 批量删除菜品，同时删除菜品对应的口味儿
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(Long[] ids){
        for (int i = 0; i < ids.length; i++) {
            dishService.removeById(ids[i]);
            LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DishFlavor::getDishId, ids[i]);
            dishFlavorService.remove(queryWrapper);
        }
        return R.success("删除菜品成功！");
    }
}
