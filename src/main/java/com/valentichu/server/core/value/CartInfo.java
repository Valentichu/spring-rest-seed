package com.valentichu.server.core.value;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * 传入购物车信息的VO
 *
 * @author Valentichu
 * created on 2017/08/30
 */
public class CartInfo implements Serializable {
    private static final long serialVersionUID = -1457625194214719960L;

    private Integer productId;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
