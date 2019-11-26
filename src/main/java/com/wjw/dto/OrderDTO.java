package com.wjw.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wjw.dataobject.OrderDetail;
import com.wjw.enums.OrderStatusEnum;
import com.wjw.enums.PayStatusEnum;
import com.wjw.utils.EnumUtil;
import com.wjw.utils.serializer.Date2LongSerializer;
import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
/**@JsonInclude(JsonInclude.Include.NON_NULL)//如果为null则不返回*/
/**
 * @author : wjwjava@163.com
 * @date : 23:22 2019/11/17
 */
public class OrderDTO {
    @Id
    /** 订单id */
    private String orderId;
    /**买家名字 */
    private String buyerName;
    /**买家手机号 */
    private String buyerPhone;
    /**买家地址 */
    private String buyerAddress;
    /**买家微信OpenId */
    private String buyerOpenid;
    /**订单总金额 */
    private BigDecimal orderAmount;
    /**订单状态,默认值为0,表示新下单 */
    private Integer orderStatus;
    /**支付状态,默认值0,表示未支付 */
    private Integer payStatus;
    /**订单的创建时间*/
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;
    /**订单更新时间*/
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;
    /**
     * 和订单详情表是一对多的关系
     */
    List<OrderDetail> orderDetailList;

    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum(){
        return EnumUtil.getByCode(orderStatus, OrderStatusEnum.class);
    }
    @JsonIgnore
    public PayStatusEnum getPayStatusEnum() {
        return EnumUtil.getByCode(payStatus, PayStatusEnum.class);
    }
}
