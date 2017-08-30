package com.valentichu.server.base.security.value;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * 登陆时的帐号信息
 *
 * @author Valentichu
 * created on 2017/08/25
 */
public class Account implements Serializable {
    private static final long serialVersionUID = 132314930571047458L;

    private String userName;
    private String userPassword;

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return this.userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
