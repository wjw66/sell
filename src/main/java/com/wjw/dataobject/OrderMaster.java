package com.wjw.dataobject;

import com.wjw.enums.OrderStatusEnum;
import com.wjw.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@DynamicUpdate//用于自动更新时间
public class OrderMaster {
    /* 订单id */
    @Id
    private String orderId;
    /*买家名字 */
    private String buyerName;
    /*买家手机号 */
    private String buyerPhone;
    /*买家地址 */
    private String buyerAddress;
    /*买家微信OpenId */
    private String buyerOpenid;
    /*订单总金额 */
    private BigDecimal orderAmount;
    /*订单状态,默认值为0,表示新下单 */
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();
    /*支付状态,默认值0,表示未支付 */
    private Integer payStatus = PayStatusEnum.WAIT.getCode();
    /*订单的创建时间*/
    private Date createTime;
    /*订单更新时间*/
    private Date updateTime;

}
