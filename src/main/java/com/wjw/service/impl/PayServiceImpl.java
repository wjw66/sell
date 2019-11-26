package com.wjw.service.impl;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.wjw.dto.OrderDTO;
import com.wjw.enums.ResultEnum;
import com.wjw.exception.SellException;
import com.wjw.service.OrderService;
import com.wjw.service.PayService;
import com.wjw.utils.JsonUtil;
import com.wjw.utils.MathUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 发起支付的业务逻辑
 *
 * @author : wjwjava@163.com
 * @date : 23:23 2019/11/17
 */
@Service
@Slf4j
public class PayServiceImpl implements PayService {
    private static final String ORDER_NAME = "豆豆家微信点餐系统";
    @Autowired
    //第三方sdk
    private BestPayServiceImpl bestPayService;
    @Autowired
    private OrderService orderService;

    @Override
    /**
     * 调用统一下单的API
     * 微信会返回一个预付单信息(prepay_id)
     */
    public PayResponse create(OrderDTO orderDTO) {
        //使用第三方sdk进行操作
        PayRequest payRequest = new PayRequest();
        //发起支付需要传的参数
        payRequest.setOpenid(orderDTO.getBuyerOpenid());
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setOrderName(ORDER_NAME);
        //BestPayTypeEnum根据支付方式传相应的枚举型
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信支付】发起支付,request={}", JsonUtil.objToJson(payRequest));

        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("【微信支付】发起支付,response={}", JsonUtil.objToJson(payResponse));
        return payResponse;
    }

    /**
     * 根据文档10.~编写
     * 1.验证签名
     * 2.判断支付状态
     * (1,2步 SDK已做)
     * 3.校验支付金额
     * 4.根据业务需求是否要求(下单人 == 支付人)
     * @param notifyData 微信的异步消息
     * @return
     */
    @Override
    public PayResponse notify(String notifyData) {
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("【微信支付】异步通知，payResponse={}", JsonUtil.objToJson(payResponse));
        //查询订单
        OrderDTO orderDTO = orderService.findOne(payResponse.getOrderId());
        //判断订单是否存在
        if (orderDTO == null){
            log.error("【微信支付】异步通知:订单不存在,orderId={}",payResponse.getOrderId());
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //判断金额是否一致
        //if (!orderDTO.getOrderAmount().equals(payResponse.getOrderAmount())){因为类型不匹配导致错误
        //result == 0 表示金额相同,!= 0 表示不同
        //if (orderDTO.getOrderAmount().compareTo(new BigDecimal(payResponse.getOrderAmount())) != 0){//由于精度的原因还是会报错
           if (!MathUtil.equalsMoney(orderDTO.getOrderAmount().doubleValue(),payResponse.getOrderAmount())){
            log.error("【微信支付】异步通知:订单金额不正确,orderId={},微信通知金额={},系统金额={}",
                    payResponse.getOrderId(),
                    payResponse.getOrderAmount(),
                    orderDTO.getOrderAmount());
            throw new SellException(ResultEnum.WECHAT_PAY_NOTIFY_ERROR);
        }
        //修改订单支付状态
        orderService.paid(orderDTO);
        return payResponse;
    }

    /**
     * 微信退款
     * @param orderDTO
     */
    @Override
    public RefundResponse refund(OrderDTO orderDTO) {
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderId(orderDTO.getOrderId());
        refundRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信退款】request={}",JsonUtil.objToJson(refundRequest));
        RefundResponse response = bestPayService.refund(refundRequest);
        log.info("【微信退款】response={}",JsonUtil.objToJson(response));
        return response;
    }
}
