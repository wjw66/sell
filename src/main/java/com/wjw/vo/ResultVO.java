package com.wjw.vo;

import lombok.Data;

/**
 * http请求返回的最外层对象
 */
@Data
public class ResultVO<T> {
    /** 错误码,0表示成功,1表示失败*/
    private Integer code;
    /** 提示信息,根据错误码返回的提示信息*/
    private String msg;
    /** 具体内容是一个object的数组,json格式返回*/
    private T data;

}
