package com.valentichu.server.core.value;

import java.io.Serializable;
import java.util.List;

public class Page implements Serializable {

    private static final long serialVersionUID = 8386265706675041431L;

    private Integer count;
    private List list;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
        this.count = list.size();
    }
}
