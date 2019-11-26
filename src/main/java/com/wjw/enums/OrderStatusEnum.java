package com.wjw.enums;

import lombok.Getter;

@Getter
public enum  OrderStatusEnum implements CodeEnum{
    NEW(0,"新下单"),
    FINISH(1,"完结"),
    CANCEL(2,"已取消"),
;
    private final Integer code;
    private final String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
