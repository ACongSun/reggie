package com.ssc.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ssc.common.R;
import com.ssc.dto.SetmealDto;
import com.ssc.entity.Category;
import com.ssc.entity.Setmeal;
import com.ssc.entity.SetmealDish;
import com.ssc.service.CategoryService;
import com.ssc.service.SetmealDishService;
import com.ssc.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName SetmealController
 * @Authoc 孙少聪
 * @Date 2022/8/22 10:52:05
 */

@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        // 构造分页器
        Page<Setmeal> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null, Setmeal::getName, name)
                    .orderByDesc(Setmeal::getUpdateTime);
        setmealService.page(pageInfo, queryWrapper);

        // 拷贝除了records
        Page<SetmealDto> setmealDtoPage = new Page<>(page, pageSize);
        BeanUtils.copyProperties(pageInfo, setmealDtoPage, "records");

        List<Setmeal> records = pageInfo.getRecords();

        List<SetmealDto> list = records.stream().map(item -> {

            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item, setmealDto);

            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                setmealDto.setCategoryName(category.getName());
            }
            return setmealDto;
        }).collect(Collectors.toList());

        setmealDtoPage.setRecords(list);

        return R.success(setmealDtoPage);
    }

    /**
     * 根据id获取套餐信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<SetmealDto> get(@PathVariable Long id){
        SetmealDto setmeal = setmealService.getSetmealWithDish(id);
        return R.success(setmeal);
    }

    /**
     * 修改套餐信息
     * @param setmealDto
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody SetmealDto setmealDto){
        log.info("信息:{}", setmealDto);
        setmealService.updateSetmealWithDish(setmealDto);
        return R.success("修改套餐信息成功！");
    }

    /**
     * 新增套餐信息
     * @param setmealDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){
        // 存储套餐表
        setmealService.save(setmealDto);

        // 存储菜品表
        Long setmealDtoId = setmealDto.getId();

        List<SetmealDish> dishList = setmealDto.getSetmealDishes();
        dishList.stream().map(item -> {
            item.setSetmealId(setmealDtoId);
            return item;
        }).collect(Collectors.toList());

        setmealDishService.saveBatch(dishList);
        return R.success("添加套餐成功！");
    }

    /**
     * 修改套餐的起售状态
     * @param status
     * @param ids
     * @return
     */
    @PostMapping("/status/{status}")
    public R<String> stopSale(@PathVariable Integer status, Long[] ids){

        for (int i = 0; i < ids.length; i++) {
            Setmeal setmeal = setmealService.getById(ids[i]);
            setmeal.setStatus(status);
            setmealService.updateById(setmeal);
        }
        return R.success("修改状态成功!");
    }


    @DeleteMapping
    public R<String> delete(Long[] ids){
        for (int i = 0; i < ids.length; i++) {
            setmealService.removeById(ids[i]);
            LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SetmealDish::getDishId, ids[i]);
            setmealDishService.remove(queryWrapper);
        }
        return R.success("删除套餐成功!");
    }
}
