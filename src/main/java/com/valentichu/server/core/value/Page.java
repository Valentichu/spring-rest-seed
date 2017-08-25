package com.valentichu.server.core.value;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.List;

/**
 * 显示页面的VO
 *
 * @author Valentichu
 * created on 2017/08/25
 */
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

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
