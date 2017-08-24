package com.valentichu.server.security.interceptor;

import com.valentichu.server.security.util.CookieUtil;
import com.valentichu.server.security.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 刷新Cookie的拦截器
 */
@Component
public class CookieRefreshInterceptor extends HandlerInterceptorAdapter {

    @Value("${jwt.header}")
    private String header;

    @Value("${jwt.expiration}")
    private Integer expiration;

    @Value("${jwt.enableCookie}")
    private boolean enableCookie;

    private final JwtTokenUtil jwtTokenUtil;

    private final CookieUtil cookieUtil;

    @Autowired
    public CookieRefreshInterceptor(JwtTokenUtil jwtTokenUtil, CookieUtil cookieUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.cookieUtil = cookieUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!enableCookie) {
            return true;
        }
        final String oldToken = cookieUtil.getValue(header, request);
        final String newToken = jwtTokenUtil.refreshToken(oldToken);
        //如果无法得到新Token,说明旧Token失效，删除作废的Cookie
        if (newToken == null) {
            cookieUtil.removeCookie(header, "/", response);
            return true;
        } else {
            cookieUtil.addCookie(header, newToken, "/", expiration, response);
            return true;
        }
    }
}
