package com.wjw.dao;

import com.wjw.dataobject.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailDaoTest {
    @Autowired
    private OrderDetailDao orderDetailDao;

    @Test
    public void saveTest() {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId(UUID.randomUUID().toString().replace("-", "").substring(0, 16));
        orderDetail.setProductName("卤肉饭");
        orderDetail.setOrderId("15a12208930e49bb");
        orderDetail.setProductId("335668");
        orderDetail.setProductIcon("www.baidu.com");
        orderDetail.setProductPrice(BigDecimal.valueOf(18.00));
        orderDetail.setProductQuantity(1);
        orderDetailDao.save(orderDetail);
    }

    @Test
    public void findByOrderIdTest() {
        List<OrderDetail> orderDetailList = orderDetailDao.findByOrderId("15a12208930e49bb");
        Assert.assertEquals(1,orderDetailList.size());
    }
}