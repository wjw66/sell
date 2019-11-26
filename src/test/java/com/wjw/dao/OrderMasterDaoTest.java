package com.wjw.dao;

import com.wjw.dataobject.OrderMaster;
import com.wjw.enums.OrderStatusEnum;
import com.wjw.enums.PayStatusEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterDaoTest {
    @Autowired
    private OrderMasterDao orderMasterDao;
    @Test
    public void saveTest(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId(UUID.randomUUID().toString().replace("-","").substring(0,16));
        orderMaster.setBuyerName("张帅");
        orderMaster.setBuyerOpenid("8322id");
        orderMaster.setBuyerAddress("上海市徐汇区");
        orderMaster.setBuyerPhone("1785645642");
        orderMaster.setOrderStatus(OrderStatusEnum.FINISH.getCode());
        orderMaster.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        orderMaster.setOrderAmount(BigDecimal.valueOf(199.83));
        orderMasterDao.save(orderMaster);
    }
    @Test
    public void findByBuyerOpenidTest(){
        //分页查询,从多少页开始,每页多少项
        PageRequest pageRequest = new PageRequest(0,2);
        Page<OrderMaster> byBuyerOpenid = orderMasterDao.findByBuyerOpenid("8322id", pageRequest);
        System.out.println(byBuyerOpenid);
    }
}