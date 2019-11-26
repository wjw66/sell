package com.wjw.service;

import com.wjw.dto.OrderDTO;

/**
 * @author : wjwjava@163.com
 * @date : 21:30 2019/11/5
 */
public interface BuyerService {
    //查询一个订单
    OrderDTO findOrderOne(String openid,String orderId);
    //取消订单
    OrderDTO cancelOrder(String openid,String oderId);
}
