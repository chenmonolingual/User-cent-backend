package com.edu.userlogin.commos;

/**
 * 全局状态码
 *
 * @author qingyan
 */
public enum ErrorCode {
    SUCCESS_CODE(200,"执行成功"),
    PARAMETER_CODE(40001,"参数错误"),
    NULL_CODE(40002,"参数为空"),
    NO_LOGIN(40003,"用户未登录"),
    NO_ADMIN_CODE(40010,"用户不是管理员权限"),
    SERVER_CODE(50001,"服务器内部错误请重新再试");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
