package com.valentichu.server.core.domain;

import java.io.Serializable;

public class Good implements Serializable {

    private static final long serialVersionUID = 7552490657503004382L;

    private Integer productId;
    private String productName;
    private Float salePrice;
    private String productImage;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Float getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(float salePrice) {
        this.salePrice = salePrice;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
}