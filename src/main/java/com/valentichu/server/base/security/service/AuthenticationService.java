package com.valentichu.server.base.security.service;

import com.valentichu.server.base.exception.ServiceException;
import com.valentichu.server.base.security.value.Account;
import com.valentichu.server.base.security.value.RegisterInfo;
import com.valentichu.server.base.security.value.UserInfo;
import org.springframework.security.authentication.BadCredentialsException;

/**
 * 鉴权Service的定义
 *
 * @author Valentichu
 * created on 2017/08/25
 */
public interface AuthenticationService {
    /**
     * 注册
     *
     * @param registerInfo 注册信息
     * @throws ServiceException 存在同名用户或注册信息不合法时抛出
     */
    void register(RegisterInfo registerInfo) throws ServiceException;

    /**
     * 登录
     *
     * @param account 帐号信息
     * @return 返回用户信息
     * @throws BadCredentialsException 验证失败时抛出
     */
    UserInfo login(Account account) throws BadCredentialsException;

    /**
     * 刷新
     *
     * @param oldToken 原有Token
     * @return 返回刷新后的Token
     * @throws ServiceException 原有Token无效时抛出
     */
    String refresh(String oldToken) throws ServiceException;
}
