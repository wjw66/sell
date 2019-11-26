package com.wjw.controller;

import com.wjw.converter.OrderForm2OrderDTO;
import com.wjw.dto.OrderDTO;
import com.wjw.enums.ResultEnum;
import com.wjw.exception.SellException;
import com.wjw.form.OrderForm;
import com.wjw.service.BuyerService;
import com.wjw.service.OrderService;
import com.wjw.utils.ResultVOUtil;
import com.wjw.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : wjwjava@163.com
 * @date : 22:19 2019/10/24
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;
    //创建订单
    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            log.error("【创建订单】参数不正确，orderForm={}",orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        OrderDTO orderDTO = OrderForm2OrderDTO.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【创建订单】 购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        OrderDTO createResult = orderService.createOrder(orderDTO);

        HashMap<String, String> map = new HashMap<>();
        map.put("orderId",createResult.getOrderId());
        return ResultVOUtil.success(map);
    }
    //订单列表

    /**
     * 需求：分页查询某个Openid下的所有订单
     *
     * @param openid
     * @param page
     * @param size
     * @return orderDTOPage.getContent()；@常用：将所有数据返回为List
     */
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam(value = "openid")String openid,
                                         @RequestParam(value = "page",defaultValue = "0")Integer page,
                                         @RequestParam(value = "size",defaultValue = "10")Integer size){
        if (StringUtils.isEmpty(openid)){
            log.error("【查询订单列表】 openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        PageRequest pageRequest = new PageRequest(page, size);
        Page<OrderDTO> orderDTOPage = orderService.findList(openid, pageRequest);
        return ResultVOUtil.success(orderDTOPage.getContent());
        //orderDTOPage.getContent(); @常用：将所有数据返回为List
        //orderDTOPage.getTotalElements();@常用：返回总数
        //orderDTOPage.getTotalPages();@常用：返回总页数
        //orderDTOPage.getSize(); @常用：返回当前页面的大小。
    }
    //订单详情(查看单个订单)
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam(value = "openid")String openid,
                                     @RequestParam(value = "orderId")String orderId){
        //为什么要传入openid?提高安全性
        OrderDTO orderDTO = buyerService.cancelOrder(openid, orderId);
        return ResultVOUtil.success(orderDTO);
    }
    //取消订单
    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam(value = "openid")String openid,
                                     @RequestParam(value = "orderId")String orderId){
        //为什么要传入openid?提高安全性
        buyerService.cancelOrder(openid,orderId);
        return ResultVOUtil.success();

    }
}
