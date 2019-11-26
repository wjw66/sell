package com.wjw.service.impl;

import com.wjw.dto.OrderDTO;
import com.wjw.service.OrderService;
import com.wjw.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author : wjwjava@163.com
 * @date : 22:43 2019/11/18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PayServiceImplTest {
    @Autowired
    private PayService payService;
    @Autowired
    private OrderService orderService;

    @Test
    public void create() {
        OrderDTO orderDTO = orderService.findOne("1572618898923ba2d02d055604409");
        payService.create(orderDTO);
    }
}