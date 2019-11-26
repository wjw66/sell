package com.wjw.service.impl;

import com.wjw.dataobject.OrderDetail;
import com.wjw.dto.OrderDTO;
import com.wjw.enums.OrderStatusEnum;
import com.wjw.enums.PayStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {
    private final String BUYER_OPENID = "1231324qaq";
    private final String ORDER_ID = "157157778987237a7a40b78284dd3";
    @Autowired
    private OrderServiceImpl orderService;

    @Test
    public void createOrder() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("王佳佳");
        orderDTO.setBuyerAddress("皖西学院");
        orderDTO.setBuyerPhone("17856416297");
        orderDTO.setBuyerOpenid(BUYER_OPENID);

        //购物车
        List<OrderDetail> orderDetailList = new ArrayList<>();

        OrderDetail o1 = new OrderDetail();
        o1.setProductId("14ea0c648a5f46ce8c2eb0420b90ee84");
        o1.setProductQuantity(1);

        OrderDetail o2 = new OrderDetail();
        o2.setProductId("9852e7d907ad412cb2c52973d1ed2f70");
        o2.setProductQuantity(1);
//        OrderDetail o3 = new OrderDetail();
//        o3.setProductId("9852e7d9-07ad-412c-b2c5-2973d1ed2f70");
//        o3.setProductQuantity(1);
        orderDetailList.add(o1);
        orderDetailList.add(o2);

        /* orderDetailList是orderDTO的属性,要记得set进去! */
        orderDTO.setOrderDetailList(orderDetailList);

        OrderDTO result = orderService.createOrder(orderDTO);
        log.info("[创建订单] result={}", result);
    }

    @Test
    public void findOne() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        System.out.println(orderDTO);
    }

    @Test
    public void findList() {
        PageRequest request = new PageRequest(0,2);
        Page<OrderDTO> orderDTOPage = orderService.findList(BUYER_OPENID, request);
        //Assert.assertNotNull(orderDTOPage);
        System.out.println(orderDTOPage);
    }

    @Test
    public void cancel() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO cancel = orderService.cancel(orderDTO);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(),cancel.getOrderStatus());
    }

    @Test
    public void finish() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO finish = orderService.finish(orderDTO);
        Assert.assertEquals(OrderStatusEnum.FINISH.getCode(),finish.getOrderStatus());
    }

    @Test
    public void paid() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.paid(orderDTO);
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(),result.getPayStatus());
    }
    @Test
    public void findAllTest() {
        PageRequest request = new PageRequest(0,2);
        //Page<OrderDTO> orderDTOPage = orderService.findList(BUYER_OPENID, request);
        Page<OrderDTO> all = orderService.findAll(request);
        Assert.assertNotNull(all);
    }
}