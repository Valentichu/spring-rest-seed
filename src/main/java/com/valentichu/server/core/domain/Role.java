package com.valentichu.server.core.domain;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.List;

public class Role implements Serializable {
    private static final long serialVersionUID = 6688729359491493613L;

    private Integer roleId;
    private String roleName;
    private List<Authority> authorities;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
