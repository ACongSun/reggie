package com.ssc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ssc.entity.User;
import com.ssc.mapper.UserMapper;
import com.ssc.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserServiceImpl
 * @Authoc 孙少聪
 * @Date 2022/8/25 09:08:33
 */

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
