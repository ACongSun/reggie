package com.ssc.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ssc.common.BaseContext;
import com.ssc.common.R;
import com.ssc.entity.ShoppingCart;
import com.ssc.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ShoppingCartController
 * @Authoc 孙少聪
 * @Date 2022/8/25 16:43:11
 */
@Slf4j
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 获取购物车列表数据
     * @return
     */
    @GetMapping("list")
    public R<List<ShoppingCart>> list(){
        Long currentId = BaseContext.getCurrentId();
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, currentId)
            .orderByDesc(ShoppingCart::getCreateTime);
        List<ShoppingCart> shoppingCartList = shoppingCartService.list(queryWrapper);
        return R.success(shoppingCartList);
    }

    /**
     * 新增购物车
     * @param shoppingCart
     * @return
     */
    @PostMapping("add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart){
        log.info("数据: {}",shoppingCart);

        // 设置用户id，判断是哪个用户添加的
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);

        // 查询当前菜品或者套餐是否存在，有的话 数量+1
        Long dishId = shoppingCart.getDishId();
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, currentId);

        if (dishId != null){
            queryWrapper.eq(ShoppingCart::getDishId, dishId);
        }else {
            queryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }
        ShoppingCart cart = shoppingCartService.getOne(queryWrapper);

        // 存在
        if (cart != null){
            Integer number = cart.getNumber();
            cart.setNumber(number+1);
            shoppingCartService.updateById(cart);
        } else { // 不存在
            shoppingCart.setNumber(1);
            shoppingCartService.save(shoppingCart);
            cart = shoppingCart;
        }

        return R.success(cart);
    }


    /**
     * 减少购物车里面的物品
     * @param shoppingCart
     * @return
     */
    @PostMapping("sub")
    public R<ShoppingCart> sub(@RequestBody ShoppingCart shoppingCart){
        log.info("传过来的数据:{}", shoppingCart);
        // 先判断是哪个人
        Long currentId = BaseContext.getCurrentId();
        Long dishId = shoppingCart.getDishId();
        Long setmealId = shoppingCart.getSetmealId();

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, currentId);
        if (dishId != null){
            queryWrapper.eq(ShoppingCart::getDishId, dishId);
        } else {
            queryWrapper.eq(ShoppingCart::getSetmealId, setmealId);
        }

        ShoppingCart shoppingCart1 = shoppingCartService.getOne(queryWrapper);

        Integer number = shoppingCart1.getNumber();

        if (number > 0){
            shoppingCart1.setNumber(number-1);
            shoppingCartService.updateById(shoppingCart1);
            if ((number-1) == 0){
                shoppingCart1.setNumber(0);
                shoppingCartService.updateById(shoppingCart1);
                shoppingCartService.remove(queryWrapper);
            }
        }
        return R.success(shoppingCart1);
    }


    @DeleteMapping("clean")
    public R<String> clean(){
        Long currentId = BaseContext.getCurrentId();

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, currentId);
        shoppingCartService.remove(queryWrapper);
        return R.success("删除完毕！！");
    }
}
