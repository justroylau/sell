package com.zhuihoude.sell.enums;

import lombok.Getter;

@Getter
public enum ProductStatusEnum {

    UP( 1 ,"上架商品！"),
    DOWN( 0,"下架商品！" )
    ;

    private Integer code;
    private String message;


    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
