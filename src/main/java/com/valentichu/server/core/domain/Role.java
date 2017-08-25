package com.valentichu.server.core.domain;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * 角色DO
 *
 * @author Valentichu
 * created on 2017/08/25
 */
public class Role implements Serializable {
    private static final long serialVersionUID = 6688729359491493613L;

    private Integer roleId;
    private String roleName;
    private Timestamp gmtCreate;
    private Timestamp gmtModified;

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

    public Timestamp getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Timestamp gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Timestamp getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Timestamp gmtModified) {
        this.gmtModified = gmtModified;
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
