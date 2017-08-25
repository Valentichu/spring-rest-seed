package com.valentichu.server.core.domain;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * 权限DO
 *
 * @author Valentichu
 * created on 2017/08/25
 */
public class Authority implements Serializable {
    private static final long serialVersionUID = -1238283404885313237L;

    private Integer authorityId;
    private String authorityName;
    private Timestamp gmtCreate;
    private Timestamp gmtModified;

    private List<Role> roles;

    public Integer getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(Integer authorityId) {
        this.authorityId = authorityId;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
