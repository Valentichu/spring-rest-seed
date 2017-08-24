package com.valentichu.server.security.service;

import com.valentichu.server.core.domain.User;

/**
 * 鉴权相关逻辑
 */
public interface AuthenticationService {
    void register(User userToAdd);
    String login(String username, String password);
    String refresh(String oldToken);
}
