package com.ss.internalcommon.dto;

import com.ss.internalcommon.constant.CommonStatusEnum;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: ljy.s
 * @Date: 2023/3/4 - 03 - 04 - 17:20
 */
@Data
// 链式调用，每次通过data去设置完(set,get)的时候，如果用了这一行注解，那么set完之后返回的是整个对象，可以接着去做别的操作
@Accessors(chain = true)
public class ResponseResult<T> {

    private int code;
    private String message;
    private T data;

    /**
     * 成功响应的方法(无参)
     * @param <T>
     * @return
     */
    public static <T> ResponseResult success(){
        return new ResponseResult().setCode(CommonStatusEnum.SUCCESS.getCode()).setMessage(CommonStatusEnum.SUCCESS.getValue());
    }

    /**
     * 成功响应的方法(有参)
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseResult success(T data) {
        return new ResponseResult().setCode(CommonStatusEnum.SUCCESS.getCode()).setMessage(CommonStatusEnum.SUCCESS.getValue()).setData(data);
    }

    /**
     * 失败：统一的失败
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseResult fail(T data){
        return new ResponseResult().setData(data);
    }

    /**
     * 失败：自定义失败 错误码和提示信息
     *
     * @param code
     * @param message
     * @return
     */
    public static ResponseResult fail(int code, String message) {
        return new ResponseResult().setCode(code).setMessage(message);
    }

    /**
     * 失败：自定义失败 错误码、提示信息、具体错误
     *
     * @param code
     * @param message
     * @param data
     * @return
     */
    public static ResponseResult fail(int code, String message, String data) {
        return new ResponseResult().setCode(code).setMessage(message).setData(data);
    }

}
