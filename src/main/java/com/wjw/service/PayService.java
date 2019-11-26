package com.wjw.service;

import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;
import com.wjw.dto.OrderDTO;

/**
 * @author : wjwjava@163.com
 * @date : 23:22 2019/11/17
 */
public interface PayService {
    /**
     * 创建订单支付
     * @param orderDTO
     */
    PayResponse create(OrderDTO orderDTO);
    /**
     * 异步接收微信支付成功消息通知
     */
    PayResponse notify(String notifyData);

    /**
     * 微信退款
     * @param orderDTO
     */
    RefundResponse refund(OrderDTO orderDTO);
}
