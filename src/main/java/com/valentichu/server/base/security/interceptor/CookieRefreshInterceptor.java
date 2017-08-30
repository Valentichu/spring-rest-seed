package com.valentichu.server.base.security.interceptor;

import com.valentichu.server.common.util.CookieUtils;
import com.valentichu.server.common.util.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 刷新Cookie的拦截器
 *
 * @author Valentichu
 * created on 2017/08/25
 */
@Component
public class CookieRefreshInterceptor extends HandlerInterceptorAdapter {
    @Value("${jwt.header}")
    private String header;
    @Value("${jwt.expiration}")
    private Integer expiration;
    @Value("${jwt.enableCookie}")
    private boolean enableCookie;

    private final JwtTokenUtils jwtTokenUtils;
    private final CookieUtils cookieUtils;

    @Autowired
    public CookieRefreshInterceptor(JwtTokenUtils jwtTokenUtils, CookieUtils cookieUtils) {
        this.jwtTokenUtils = jwtTokenUtils;
        this.cookieUtils = cookieUtils;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!enableCookie) {
            return true;
        }

        final String oldToken = cookieUtils.getValue(header, request);
        if (oldToken == null || oldToken.equals("")) {
            return true;
        }

        final String newToken = jwtTokenUtils.refreshToken(oldToken);
        //如果无法得到新Token,说明旧Token失效，删除作废的Cookie
        if (newToken == null) {
            cookieUtils.removeCookie(header, "/", response);
            return true;
        }

        cookieUtils.addCookie(header, newToken, "/", expiration, response);
        return true;
    }
}
