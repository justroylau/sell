package com.zhuihoude.sell.dto;

import lombok.Data;

/**
     * 购物车
     */
@Data
public class ShoppingCartDTO {

    /** 商品ID. */
    private String productId;

    /** 商品数量. */
    private Integer productQuantity;

    public ShoppingCartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}