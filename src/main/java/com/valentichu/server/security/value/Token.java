package com.valentichu.server.security.value;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

public class Token implements Serializable {
    private static final long serialVersionUID = -8217639949507667431L;

    private String token;

    public Token(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
