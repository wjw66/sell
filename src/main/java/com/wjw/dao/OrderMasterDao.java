package com.wjw.dao;

import com.wjw.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMasterDao extends JpaRepository<OrderMaster,String> {
    /**
     * 分页查询某个人所有的订单,
     * 如果不传pageable会把某人所有订单一次都查出来,数据量太大
     */
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);

}
