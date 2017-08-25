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
     */
    void saveUser(User user);
}

