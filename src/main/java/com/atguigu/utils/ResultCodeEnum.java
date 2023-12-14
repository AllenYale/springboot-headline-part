package com.atguigu.utils;

/**
 * ClassName: ResultEnum
 * PackageName: com.atguigu.utils
 * Description:
 *
 * @Author: Hanyu
 * @Date: 23/12/14 - 13:11
 * @Version: v1.0
 */

/**
 * 这是一个定义返回结果中：返回code 和message的枚举类
 * @author hanyu
 *
 */
public enum ResultCodeEnum {
    SUCCESS(200,"success"),
    USERNAME_ERROR(501,"usernameError"),
    PASSWORD_ERROR(503,"passwordError"),
    NOT_LOGIN(504,"notLogin"),
    USERNAME_USED(505,"userNameUsed")
    ;
    private Integer code;
    private String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
