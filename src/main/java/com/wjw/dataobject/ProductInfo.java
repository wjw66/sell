package com.wjw.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
public class ProductInfo {
    @Id
    private String productId;
    private String productName;
    //商品价格用BigDecimal
    private BigDecimal productPrice;
    //商品库存
    private Integer productStock;
    //商品描述
    private String productDescription;
    //商品图片
    private String productIcon;
    //商品状态,0正常1下架
    private Integer productStatus;
    //类别编号
    private Integer categoryType;
}
