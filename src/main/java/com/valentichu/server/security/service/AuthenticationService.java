package com.valentichu.server.security.service;

import com.valentichu.server.base.exception.ServiceException;
import com.valentichu.server.core.domain.User;
import com.valentichu.server.security.value.Account;
import org.springframework.security.authentication.BadCredentialsException;

public interface AuthenticationService {
    void register(User userToAdd) throws ServiceException;

    String login(Account account) throws BadCredentialsException;

    String refresh(String oldToken) throws ServiceException;
}
