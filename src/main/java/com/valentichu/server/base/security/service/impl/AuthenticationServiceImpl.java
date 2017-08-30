package com.valentichu.server.base.security.service.impl;

import com.valentichu.server.base.exception.ServiceException;
import com.valentichu.server.core.domain.User;
import com.valentichu.server.core.mapper.UserMapper;
import com.valentichu.server.base.security.service.AuthenticationService;
import com.valentichu.server.common.util.JwtTokenUtils;
import com.valentichu.server.base.security.value.Account;
import com.valentichu.server.base.security.value.RegisterInfo;
import com.valentichu.server.base.security.value.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;

/**
 * 鉴权Service的实现
 *
 * @author Valentichu
 * created on 2017/08/25
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtils jwtTokenUtils;
    private final UserMapper userMapper;

    @Autowired
    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, JwtTokenUtils jwtTokenUtils, UserMapper userMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtils = jwtTokenUtils;
        this.userMapper = userMapper;
    }

    @Override
    public void register(RegisterInfo registerInfo) throws ServiceException {
        final String userName = registerInfo.getUserName();
        final String rawPassword = registerInfo.getUserPassword();
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(rawPassword)) {
            throw new ServiceException("注册用户名或密码不能为空");
        }
        if (userMapper.getUser(userName) != null) {
            throw new ServiceException("已有该用户");
        }

        User userToAdd = new User();
        userToAdd.setUserName(userName);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userToAdd.setUserPassword(encoder.encode(rawPassword));
        userToAdd.setRoleId(1);
        userToAdd.setGmtCreate(new Timestamp(System.currentTimeMillis()));
        userMapper.saveUser(userToAdd);
    }

    @Override
    public UserInfo login(Account account) throws BadCredentialsException {
        final String userName = account.getUserName();
        final String password = account.getUserPassword();
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(userName)) {
            throw new ServiceException("用户名或密码不能为空");
        }

        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(userName, password);
        //此处如果校验失败会抛出BadCredentialsException由错误统一处理类返回给用户
        authenticationManager.authenticate(upToken);
        final String token = jwtTokenUtils.generateToken(userName);

        return new UserInfo(userName, token);
    }

    @Override
    public String refresh(String oldToken) throws ServiceException {
        if (StringUtils.isEmpty(oldToken)) {
            throw new ServiceException("Token无效");
        }

        final String refreshedToken = jwtTokenUtils.refreshToken(oldToken);
        if (StringUtils.isEmpty(refreshedToken)) {
            throw new ServiceException("Token已过期或无效");
        }

        return refreshedToken;
    }
}

