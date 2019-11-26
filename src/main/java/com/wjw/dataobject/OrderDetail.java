package com.wjw.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
public class OrderDetail {
    @Id
    /*订单详情表主键*/
    private String detailId;
    /*订单id*/
    private String orderId;
    /*商品id*/
    private String productId;
    /*商品名称*/
    private String productName;
    /*商品价格*/
    private BigDecimal productPrice;
    /*商品数量*/
    private Integer productQuantity;
    /*商品图片*/
    private String productIcon;

}
