package com.valentichu.server.core.domain;

import java.io.Serializable;
import java.util.List;

public class Authority implements Serializable {

    private static final long serialVersionUID = -1238283404885313237L;

    private Integer authorityId;
    private String authorityName;
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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
