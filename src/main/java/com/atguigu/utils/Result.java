package com.atguigu.utils;

/**
 * ClassName: Result
 * PackageName: com.atguigu.utils
 * Description:
 *  返回结果封装类
 * @Author: Hanyu
 * @Date: 23/12/14 - 13:05
 * @Version: v1.0
 */
public class Result<T> {
    //返回码
    private Integer code;
    //返回信息
    private String message;
    //返回数据
    private T data;

    public Result() {
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    //返回数据
    public static <T> Result<T> build(T data){
        Result<T> tResult = new Result<>();
        if(data != null){
            tResult.setData(data);
        }
        return tResult;
    }

    public static <T> Result<T> build(T data, Integer code, String message){
        Result<T> build = build(data);
        build.setCode(code);
        build.setMessage(message);
        return build;
    }

    /**
     根据枚举给result赋值
     */
    public static <T> Result<T> build(T data, ResultCodeEnum resultEnum){
        Result<T> build = build(data);
        build.setMessage(resultEnum.getMessage());
        build.setCode(resultEnum.getCode());
        return build;
    }

    /**
     * 操作成功
     * @param data
     * @return
     * @param <T>
     */
    public static <T> Result<T> ok(T data){
        return build(data, ResultCodeEnum.SUCCESS);
    }

    public Result<T> message(String msg){
        this.message = msg;
        return this;
    }

    public Result<T> code(Integer code){
        this.code = code;
        return this;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
