package com.valentichu.server.base.exception.handler;

import com.valentichu.server.base.exception.ServiceException;
import com.valentichu.server.common.value.Result;
import com.valentichu.server.common.value.ResultCodeEnum;
import com.valentichu.server.base.configurer.WebMvcConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * 统一异常处理（Security Filter异常除外）
 *
 * @author Valentichu
 * created on 2017/08/25
 */
@RestControllerAdvice
public class DefaultExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    @ExceptionHandler(value = ServiceException.class)
    public Result serviceException(HttpServletRequest request, Object handler, Exception e) {
        Result result = new Result();
        result.setCode(ResultCodeEnum.FAIL).setMessage(e.getMessage());
        logger.info(e.getMessage());
        return result;
    }

    @ExceptionHandler(value = NoHandlerFoundException.class)
    public Result noHandlerFoundException(HttpServletRequest request, Object handler, Exception e) {
        Result result = new Result();
        result.setCode(ResultCodeEnum.NOT_FOUND).setMessage("接口 [" + request.getRequestURI() + "] 不存在");
        return result;
    }

    @ExceptionHandler(value = ServletException.class)
    public Result servletException(HttpServletRequest request, Object handler, Exception e) {
        Result result = new Result();
        result.setCode(ResultCodeEnum.FAIL).setMessage(e.getMessage());
        return result;
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    public Result badCredentialsException(HttpServletRequest request, Object handler, Exception e) {
        Result result = new Result();
        result.setCode(ResultCodeEnum.UNAUTHORIZED).setMessage("未通过身份认证");
        return result;
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public Result accessDeniedException(HttpServletRequest request, Object handler, Exception e) {
        Result result = new Result();
        result.setCode(ResultCodeEnum.FORBIDDEN).setMessage("不允许访问");
        return result;
    }

    @ExceptionHandler(value = Exception.class)
    public Result exception(HttpServletRequest request, Object handler, Exception e) {
        Result result = new Result();
        result.setCode(ResultCodeEnum.INTERNAL_SERVER_ERROR).setMessage("接口 [" + request.getRequestURI() + "] 内部错误，请联系管理员");
        String message;
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            message = String.format("接口 [%s] 出现异常，方法：%s.%s，异常摘要：%s",
                    request.getRequestURI(),
                    handlerMethod.getBean().getClass().getName(),
                    handlerMethod.getMethod().getName(),
                    e.getMessage());
        } else {
            message = e.getMessage();
        }
        logger.error(message, e);
        return result;
    }
}



