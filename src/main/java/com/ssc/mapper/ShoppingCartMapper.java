package com.ssc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ssc.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {
}
