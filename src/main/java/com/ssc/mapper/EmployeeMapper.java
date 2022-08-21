package com.ssc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ssc.entity.Employee;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
