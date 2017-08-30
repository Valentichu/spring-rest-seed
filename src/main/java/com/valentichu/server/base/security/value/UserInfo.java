package com.valentichu.server.base.security.value;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * 返回给用户的Token
 *
 * @author Valentichu
 * created on 2017/08/25
 */
public class UserInfo implements Serializable {
    private static final long serialVersionUID = -8217639949507667431L;

    private String token;
    private String userName;

    public UserInfo(String token) {
        this.token = token;
    }

    public UserInfo(String userName, String token) {
        this.userName = userName;
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

    public String getUserName() {
        return this.userName;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
