package com.valentichu.server.security.service.impl;

import com.valentichu.server.base.exception.ServiceException;
import com.valentichu.server.core.domain.User;
import com.valentichu.server.core.mapper.UserMapper;
import com.valentichu.server.security.service.AuthenticationService;
import com.valentichu.server.security.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 鉴权相关逻辑
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
    public void register(User userToAdd) {
        final String username = userToAdd.getUserName();
        if (userMapper.getUser(username) != null) {
            return;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPassword = userToAdd.getUserPwd();
        userToAdd.setUserPwd(encoder.encode(rawPassword));
        userToAdd.setRoleId(1);
        userMapper.insertUser(userToAdd);
    }

    @Override
    public String login(String username, String password) {
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        authenticationManager.authenticate(upToken);
        return jwtTokenUtil.generateToken(username);
    }

    @Override
    public String refresh(String oldToken) {
        if (!jwtTokenUtil.isTokenExpired(oldToken)) {
            return jwtTokenUtil.refreshToken(oldToken);
        } else {
            throw new ServiceException("Token已过期");
        }
    }
}
