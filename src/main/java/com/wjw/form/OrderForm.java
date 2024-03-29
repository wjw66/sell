package com.wjw.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author : wjwjava@163.com
 * @date : 16:50 2019/10/27
 * 传参,用于映射前台提交的form表单
 */
@Data
public class OrderForm {
    @NotEmpty(message = "姓名必填")
    private String name;
    @NotEmpty(message = "手机号必填")
    private String phone;
    @NotEmpty(message = "地址必填")
    private String address;
    @NotEmpty(message = "openid必填")
    private String openid;
    /**
     * 购物车
     */
    @NotEmpty(message = "购物车不能为空")
    private String items;
}
