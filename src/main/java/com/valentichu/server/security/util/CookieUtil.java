package com.valentichu.server.security.util;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 处理Cookie的工具类
 */
@Component
public class CookieUtil {
    public String getValue(String key, HttpServletRequest request) {
        final Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        Cookie cookie = null;
        for (Cookie _cookie : cookies) {
            if (_cookie.getName().equals(key)) {
                cookie = _cookie;
                break;
            }
        }
        if (cookie == null) {
            return null;
        }
        return cookie.getValue();
    }

    public void addCookie(String key, String value, String path, int maxAge, HttpServletResponse response) {
        Cookie cookie = new Cookie(key, value);
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    public void removeCookie(String key, String path, HttpServletResponse response) {
        addCookie(key, null, path, 0, response);
    }
}
