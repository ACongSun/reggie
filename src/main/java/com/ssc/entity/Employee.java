package com.ssc.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @ClassName Employee
 * @Authoc 孙少聪
 * @Date 2022/8/20 09:16:10
 */

@Data
public class Employee {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String name;

    private String password;

    private String phone;

    private String sex;

    private String idNumber;

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    // 插入时自动填充
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    // 更新时自动填充
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;
}
