package com.valentichu.server.core.service.impl;

import com.valentichu.server.core.domain.CartItem;
import com.valentichu.server.core.mapper.UserMapper;
import com.valentichu.server.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户Service的实现
 *
 * @author Valentichu
 * created on 2017/08/30
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public void addCart(String userName, Integer productId) {
        Integer cartItemNum = userMapper.getCartItemNum(userName, productId);
        if (cartItemNum == null) {
            userMapper.insertCartItem(userName, productId);
        } else {
            cartItemNum++;
            userMapper.updateCartItemNum(userName, productId, cartItemNum);
        }
    }

    @Override
    public List<CartItem> listCartItem(String userName) {
        return userMapper.listCart(userName);
    }

    @Override
    public void updateCartItem(String userName, int productId, String action, int value) {
        if (action.equals("num")) {
            userMapper.updateCartItemNum(userName, productId, value);
        } else {
            userMapper.updateCartItemChecked(userName, productId, value);
        }
    }

    @Override
    public void updateCartChecked(String userName, int checked) {
        userMapper.updateCartChecked(userName, checked);
    }

    @Override
    public void deleteCartItem(String userName, int productId) {
        userMapper.deleteCartItem(userName, productId);
    }
}
