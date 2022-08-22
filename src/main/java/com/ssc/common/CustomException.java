package com.ssc.common;

/**
 * @ClassName CustomException
 * @Authoc 孙少聪
 * @Date 2022/8/22 11:11:12
 */

public class CustomException extends  RuntimeException{
    public CustomException(String message){
        super(message);
    }
}
