package com.valentichu.server.core.util;

import com.valentichu.server.base.exception.ServiceException;
import com.valentichu.server.common.util.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 取Token中的信息的工具类
 *
 * @author Valentichu
 * created on 2017/08/29
 */
@Component
public class TokenUtils {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Value("${jwt.header}")
    private String header;

    private final JwtTokenUtils jwtTokenUtils;

    @Autowired
    public TokenUtils(JwtTokenUtils jwtTokenUtils) {
        this.jwtTokenUtils = jwtTokenUtils;
    }

    public String getUserNameFromToken(String token) throws ServiceException {
        if (StringUtils.isEmpty(token) || !token.startsWith(tokenHead)) {
            throw new ServiceException("Token无效");
        }

        String userName = jwtTokenUtils.getUserNameFromToken(token);
        if (StringUtils.isEmpty(userName)) {
            throw new ServiceException("Token无效");
        }

        return userName;
    }
}
