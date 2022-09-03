package com.ssc.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName RedisTest
 * @Authoc 孙少聪
 * @Date 2022/9/3 09:29:42
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testRedis(){
        ValueOperations opsForValue = redisTemplate.opsForValue();
        opsForValue.set("username","ssc");
        String username = (String) opsForValue.get("username");
        System.out.println(username);
    }
}
