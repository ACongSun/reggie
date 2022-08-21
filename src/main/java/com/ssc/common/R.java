package com.ssc.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName R 通用的返回数据
 * @Authoc 孙少聪
 * @Date 2022/8/20 09:39:19
 */

@Data
public class R<T> {

    private Integer code; //编码：1成功，0和其它数字为失败

    private String msg; //错误信息

    private T data; //数据 用泛型增加通用性

    private Map map = new HashMap(); //动态数据

    // 请求成功
    public static <T> R<T> success(T object) {
        R<T> r = new R<T>();
        r.data = object;
        r.code = 1;
        return r;
    }

    // 请求失败
    public static <T> R<T> error(String msg) {
        R r = new R();
        r.msg = msg;
        r.code = 0;
        return r;
    }

    // 操作动态数据
    public R<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

}
