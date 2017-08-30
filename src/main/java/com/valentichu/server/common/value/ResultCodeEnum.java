package com.valentichu.server.common.value;

/**
 * 响应码枚举，参考HTTP状态码的语义
 *
 * @author Valentichu
 * created on 2017/08/25
 */
public enum ResultCodeEnum {
    //成功
    SUCCESS(200),
    //失败
    FAIL(400),
    //不允许访问
    FORBIDDEN(403),
    //未认证（签名错误）
    UNAUTHORIZED(401),
    //接口不存在
    NOT_FOUND(404),
    //服务器内部错误
    INTERNAL_SERVER_ERROR(500);

    public Integer code;

    ResultCodeEnum(Integer code) {
        this.code = code;
    }
}
