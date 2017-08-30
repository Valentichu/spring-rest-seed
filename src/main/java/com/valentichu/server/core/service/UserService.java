package com.valentichu.server.core.service;

import com.valentichu.server.base.exception.ServiceException;
import com.valentichu.server.core.domain.Good;

import java.util.List;

/**
 * 用户Service的定义
 *
 * @author Valentichu
 * created on 2017/08/29
 */
public interface UserService {
    /**
     * 列出商品
     *
     * @param userName  用户名
     * @param productId 商品Id
     */
    void addCart(String userName, Integer productId);
}
