package com.wjw.enums;

import lombok.Getter;

/**
 * 商品状态
 * 说明:枚举类不能被实例化
 */
@Getter
public enum ProductStatusEnum {
    UP(0,"在架"),
    DOWN(1,"下架")
    ;
    private final Integer code;
    private final String message;
    ProductStatusEnum(Integer code,String message){
        this.code = code;
        this.message = message;
    }
}
