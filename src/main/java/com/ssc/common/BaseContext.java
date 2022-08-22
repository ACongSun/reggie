package com.ssc.common;

/**
 * 基于ThreadLocal封装的一个工具类,获取用户id
 * @ClassName BaseContext
 * @Authoc 孙少聪
 * @Date 2022/8/22 09:26:33
 */

public class BaseContext {

    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
