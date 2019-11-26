package com.wjw.controller;

import com.lly835.bestpay.model.PayResponse;
import com.wjw.dto.OrderDTO;
import com.wjw.enums.ResultEnum;
import com.wjw.exception.SellException;
import com.wjw.service.OrderService;
import com.wjw.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.Objects;

/**
 * 微信支付
 * @author : wjwjava@163.com
 * @date : 23:05 2019/11/17
 */
@Controller
@RequestMapping("/pay")
public class PayController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private PayService payService;
    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId")String orderId,
                               @RequestParam("returnUrl")String returnUrl,
                               Map<String,Object> map){
        //1.通过传入的orderId来查询订单
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (Objects.isNull(orderDTO)){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //2.发起支付
        PayResponse payResponse = payService.create(orderDTO);
        map.put("payResponse",payResponse);
        map.put("returnUrl",returnUrl);
        //返回到resources/templates/pay/create.ftl的页面(freemark的使用方式)
        return new ModelAndView("pay/create",map);
    }
    //异步接收微信支付成功的通知

    /**
     * 微信异步通知
     * @param notifyData
     */
    @PostMapping("notify")
    public ModelAndView notify(@RequestBody String notifyData){
        //调用业务层方法
        payService.notify(notifyData);
        //返回给微信处理结果
        return new ModelAndView("pay/success");
    }
}
