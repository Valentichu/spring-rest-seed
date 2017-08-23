package com.valentichu.server.security.value;

import java.io.Serializable;

public class Account implements Serializable {

    private static final long serialVersionUID = 132314930571047458L;

    private String userName;
    private String userPwd;

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return this.userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }
}
