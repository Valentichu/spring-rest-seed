package com.valentichu.server.security.service.impl;

import com.valentichu.server.base.exception.ServiceException;
import com.valentichu.server.core.domain.User;
import com.valentichu.server.core.mapper.UserMapper;
import com.valentichu.server.security.service.AuthenticationService;
import com.valentichu.server.security.util.JwtTokenUtil;
import com.valentichu.server.security.value.Account;
import com.valentichu.server.security.value.RegisterInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
    private final JwtTokenUtil jwtTokenUtil;
    private final UserMapper userMapper;

    @Autowired
    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, UserMapper userMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userMapper = userMapper;
    }

    @Override
    public void register(RegisterInfo registerInfo) throws ServiceException {
        final String username = registerInfo.getUserName();
        final String rawPassword = registerInfo.getUserPassword();
        if (username == null || username.equals("") || rawPassword == null || rawPassword.equals("")) {
            throw new ServiceException("注册用户名或密码不能为空");
        }
        if (userMapper.getUser(username) != null) {
            throw new ServiceException("已有该用户");
        }

        User userToAdd = new User();
        userToAdd.setUserName(username);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userToAdd.setUserPassword(encoder.encode(rawPassword));
        userToAdd.setRoleId(1);
        userToAdd.setGmtCreate(new Timestamp(System.currentTimeMillis()));
        userMapper.saveUser(userToAdd);
    }

    @Override
    public String login(Account account) throws BadCredentialsException {
        final String username = account.getUserName();
        final String password = account.getUserPassword();
        if (username == null || username.equals("") || password == null || password.equals("")) {
            throw new ServiceException("用户名或密码不能为空");
        }

        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        //此处如果校验失败会抛出BadCredentialsException由错误统一处理类返回给用户
        authenticationManager.authenticate(upToken);
        return jwtTokenUtil.generateToken(username);
    }

    @Override
    public String refresh(String oldToken) throws ServiceException {
        if (oldToken == null || oldToken.equals("")) {
            throw new ServiceException("Token无效");
        }

        final String refreshedToken = jwtTokenUtil.refreshToken(oldToken);
        if (refreshedToken == null) {
            throw new ServiceException("Token已过期或无效");
        }

        return refreshedToken;
    }
}

