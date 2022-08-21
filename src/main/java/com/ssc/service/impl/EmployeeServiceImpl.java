package com.ssc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ssc.entity.Employee;
import com.ssc.mapper.EmployeeMapper;
import com.ssc.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * @ClassName EmployeeServiceImpl
 * @Authoc 孙少聪
 * @Date 2022/8/20 09:24:37
 */

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

}
