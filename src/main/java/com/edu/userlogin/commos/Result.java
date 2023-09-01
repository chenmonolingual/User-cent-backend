package com.edu.userlogin.commos;


import lombok.Data;
import java.io.Serializable;

/**
 * 统一结果返回类
 *
 * code: number;
 *     data: T;
 *     message: string;
 *     description: string;
 * @param <T>
 *
 * @author qingyan
 */
@Data
public class Result<T> implements Serializable {
    //用户状态
    private Integer code;
    //返回的数据
    private T data;
    //消息
    private String message;
    //描述
    private String description;


    public static <T> Result<T> success(T Object){
        Result<T> r = new Result<>();
        r.code = ErrorCode.SUCCESS_CODE.getCode();
        r.data = Object;
        r.message  = ErrorCode.SUCCESS_CODE.getMessage();
        return r;
    }
    public static <T> Result<T> error(ErrorCode errorCode,String description){
        Result<T> r = new Result<>();
        r.code = errorCode.getCode();
        r.data = null;
        r.message  =  errorCode.getMessage();
        r.description = description;
        return r;
    }

    public static <T> Result<T> error(ErrorCode errorCode){
        Result<T> r = new Result<>();
        r.code = errorCode.getCode();
        r.data = null;
        r.message  =  errorCode.getMessage();
        return r;
    }
}
