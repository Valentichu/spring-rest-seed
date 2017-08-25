package com.valentichu.server.base.exception;

/**
 * 服务（业务）异常如“ 账号或密码错误 ”，该异常只做INFO级别的日志记录*
 *
 * @author Valentichu
 * created on 2017/08/25
 */
public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 4606055141396589009L;

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
