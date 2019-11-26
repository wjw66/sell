package com.wjw.service;

import com.wjw.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
/**
 * @author : wjwjava@163.com
 * @date : 23:23 2019/11/17
 */
public interface OrderService {
    /**
     * 创建订单
     * @param orderDTO
     * @return
     */
    OrderDTO createOrder(OrderDTO orderDTO);

    /**
     * 查询单个订单
     * @param orderId 订单ID
     * @return OrderDTO对象
     */
    OrderDTO findOne(String orderId);
    /**
     * 查询订单列表
     * @param buyerOpenid 买家的openid
     * @param pageable 分页参数
     * @return List集合
     */
    Page<OrderDTO> findList(String buyerOpenid, Pageable pageable);

    /**
     * 查询所有订单
     * @param pageable
     * @return
     */
    Page<OrderDTO> findAll(Pageable pageable);
    /**
     * 取消订单
     * @param orderDTO
     * @return
     */
    OrderDTO cancel(OrderDTO orderDTO);

    /* 完结订单 */
    OrderDTO finish(OrderDTO orderDTO);

    /* 支付订单 */
    OrderDTO paid(OrderDTO orderDTO);
}
