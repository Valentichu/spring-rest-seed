package com.valentichu.server.core.service.impl;

import com.valentichu.server.base.exception.ServiceException;
import com.valentichu.server.core.service.UserService;
import com.valentichu.server.core.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 用户Service的实现
 *
 * @author Valentichu
 * created on 2017/08/27
 */
@Service
public class UserServiceImpl implements UserService {
    private final TokenUtils tokenUtils;

    @Autowired
    public UserServiceImpl(TokenUtils tokenUtils) {
        this.tokenUtils = tokenUtils;
    }
}
