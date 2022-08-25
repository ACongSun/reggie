package com.ssc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ssc.entity.User;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserMapper extends BaseMapper<User> {
}
