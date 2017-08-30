package com.valentichu.server.core.mapper;

import com.valentichu.server.core.domain.User;
import org.apache.ibatis.annotations.Param;

/**
 * 用户相关数据库接口
 *
 * @author Valentichu
 * created on 2017/08/25
 */
public interface UserMapper {
    /**
     * 从数据库中取出用户信息
     *
     * @param userName 用户名
     * @return 用户信息
     */
    User getUser(@Param("userName") String userName);

    /**
     * 向数据库保存用户
     *
     * @param user 要保存的用户
     * @return 用户主键
     */
    int saveUser(User user);

    /**
     * 向购物车中加入新商品
     *
     * @param userName  用户名
     * @param productId 商品Id
     */
    void insertCartItem(@Param("userName") String userName,
                        @Param("productId") Integer productId);

    /**
     * 更新购物车已有商品的数量
     *
     * @param userName   用户名
     * @param productId  商品Id
     * @param productNum 商品数量
     * @return 影响的行数，应该为1
     */
    int updateCartItemNum(@Param("userName") String userName,
                          @Param("productId") Integer productId,
                          @Param("productNum") Integer productNum);


    /**
     * 取得购物车中的商品数量
     *
     * @param userName  用户名
     * @param productId 商品Id
     * @return 商品数量，可能为Null，需要对返回值进行判断
     */
    Integer getCartItemNum(@Param("userName") String userName,
                           @Param("productId") Integer productId);
}

