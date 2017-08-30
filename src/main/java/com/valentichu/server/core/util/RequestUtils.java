package com.valentichu.server.core.util;

import com.valentichu.server.base.exception.ServiceException;
import com.valentichu.server.common.util.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 取Token中的信息的工具类
 *
 * @author Valentichu
 * created on 2017/08/29
 */
@Component
public class RequestUtils {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Value("${jwt.header}")
    private String header;

    private final JwtTokenUtils jwtTokenUtils;

    @Autowired
    public RequestUtils(JwtTokenUtils jwtTokenUtils) {
        this.jwtTokenUtils = jwtTokenUtils;
    }

    public String getUserNameFromHeader(HttpServletRequest request) throws ServiceException {
        if (request == null) {
            throw new ServiceException("请求无效");
        }
        final String token = request.getHeader(header);
        if (StringUtils.isEmpty(token) || !token.startsWith(tokenHead)) {
            throw new ServiceException("Token无效");
        }
        final String userName = jwtTokenUtils.getUserNameFromToken(token);
        if (StringUtils.isEmpty(userName)) {
            throw new ServiceException("Token无效");
        }
        return userName;
    }
}
