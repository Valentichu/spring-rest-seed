package com.valentichu.server.core.service;

import com.valentichu.server.base.exception.ServiceException;
import com.valentichu.server.core.domain.CartItem;
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
     * 向购物车中加入商品
     *
     * @param userName  用户名
     * @param productId 商品Id
     */
    void addCart(String userName, Integer productId);

    /**
     * 获取购物车项目信息
     *
     * @param userName 用户名
     * @return 购物车项目List
     */
    List<CartItem> listCartItem(String userName);

    /**
     * 更新购物车项目信息
     *
     * @param userName  用户名
     * @param productId 商品id
     * @param action    更新数量or选中
     * @param value     更新的具体值
     */
    void updateCartItem(String userName, int productId, String action, int value);

    /**
     * 更新购物车所有项目的选中状态
     *
     * @param userName 用户名
     * @param checked  选中or不选中
     */
    void updateCartChecked(String userName, int checked);

    /**
     * 从购物车中删除商品
     *
     * @param userName  用户名
     * @param productId 商品Id
     */
    void deleteCartItem(String userName, int productId);
}
