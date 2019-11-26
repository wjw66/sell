package com.wjw.dao;

import com.wjw.dataobject.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailDao extends JpaRepository<OrderDetail, String> {
    /**
     * 两表联查,通过order_master表查到order_id,再从order_detail表中查出所有订单的详情
     * order_master表中一个order_id对应order_detail表中多个结果(一个订单包含多个商品)
     * 返回的是一个list
     */
    List<OrderDetail> findByOrderId(String orderId);
}
