package com.ssc.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ssc.common.R;
import com.ssc.entity.Employee;
import com.ssc.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName EmployeeController
 * @Authoc 孙少聪
 * @Date 2022/8/20 09:36:04
 */

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 新增员工
     * @param employee
     * @return
     */
    @PostMapping
    public R<String> save(Employee employee){
        log.info("新增员工。。。{}", employee.toString());
        return null;
    }


    /**
     * 员工登录
     * @param request HttpServletRequest 员工对象的id存在session中一份
     * @param employee
     * @return
     */
    @PostMapping("login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        // md5 加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        // 根据用户名查数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);

        // 判断是否查到
        if (emp == null){
            return R.error("登陆失败！！！");
        }
        // 比对密码
        if (!emp.getPassword().equals(password)){
            return   R.error("密码错误！！！");
        }
        // 查看状态
        if (emp.getStatus() == 0){
            return R.error("账号被禁用！！！");
        }
        // 登陆成功 id存入session
        request.getSession().setAttribute("employee", emp.getId());

        return R.success(emp);
    }

    /**
     * 推出登录，清除存入session中的id
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        // 清理session中的id
        request.getSession().removeAttribute("employee");
        return R.success("退出成功。。。");
    }

}
