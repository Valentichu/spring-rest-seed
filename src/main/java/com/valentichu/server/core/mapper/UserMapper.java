package com.valentichu.server.core.mapper;

import com.valentichu.server.core.domain.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    User getUser(@Param("userName") String userName);

    void insertUser(User user);
}
