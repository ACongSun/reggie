package com.ssc.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ssc.common.R;
import com.ssc.entity.User;
import com.ssc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName UserController
 * @Authoc 孙少聪
 * @Date 2022/8/25 09:09:32
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 获取验证码，存入seesion
     * @param user
     * @param session
     * @return
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){
        String code = (int)((Math.random()*9+1)*100000)+"";
        session.setAttribute("phone",user.getPhone());
        session.setAttribute("code",code);

        // 缓存至redis中去
        redisTemplate.opsForValue().set("phone",user.getPhone(),5,TimeUnit.MINUTES);
        redisTemplate.opsForValue().set("code",code,5,TimeUnit.MINUTES);
        return R.success(code);
    }

    /**
     * 移动端登录
     * @param map
     * @param session
     * @return
     */
    @PostMapping("/login")
    public R<String> login(@RequestBody Map<String, String> map, HttpSession session){
        // 获取session中的手机号和验证码
        String phone = map.get("phone");
        // 与验证码比对
        String code = map.get("code");
        // String  sesionCode = (String) session.getAttribute("code");
        // 从redis中拿到验证码
        String  sesionCode =(String) redisTemplate.opsForValue().get("code");
        // 验证码一直比对成功登陆成功
        if (sesionCode.equals(code) && sesionCode != null){
            // 新用户自动注册
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone, phone);
            User user = userService.getOne(queryWrapper);
            if (user == null){
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
            session.setAttribute("user", user.getId());

            // 登录成功
            redisTemplate.delete("code");
            redisTemplate.delete("phone");
            return R.success("登陆成功！！");
        }
        return R.error("未知错误！");
    }
}
