package com.ssc.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ssc.common.R;
import com.ssc.entity.Category;
import com.ssc.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName CategoryController
 * @Authoc 孙少聪
 * @Date 2022/8/22 09:55:11
 */

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增菜品分类
     * @param category
     * @return
     */
    @PostMapping
    public R<String> add(@RequestBody Category category){
        log.info("信息:{}", category);
        categoryService.save(category);
        return R.success("新增分类信息成功");
    }

    /**
     * 菜品分页查询
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize){
        // 分页构造器
        Page<Category> pageInfo = new Page<>(page, pageSize);

        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Category::getSort);

        categoryService.page(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 根据id删除，判断是否被关联
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(Long ids){
        categoryService.remove(ids);
        return R.success("删除分类信息成功！！");
    }


    /**
     * 修改菜品信息
     * @param category
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody Category category){
        categoryService.updateById(category);
        return R.success("修改分类信息成功");
    }

}
