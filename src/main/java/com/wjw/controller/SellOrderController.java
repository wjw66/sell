package com.wjw.controller;

import com.wjw.dto.OrderDTO;
import com.wjw.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @author : wjwjava@163.com
 * @date : 0:34 2019/11/25
 * @description : 卖家端Controller
 */
@Controller
@RequestMapping("/seller/order")
@Slf4j
public class SellOrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 卖家端,订单列表(分页查询所有订单)
     * @param page 页数,默认从第一页开始
     * @param size 每页数量
     * @param map ModelAndView的显示内容
     * @return
     */
    @GetMapping("/list")
    public ModelAndView orderList(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                  @RequestParam(value = "size",defaultValue = "5")Integer size,
                                  Map<String,Object> map){
        PageRequest pageRequest = new PageRequest(page - 1,size);
        Page<OrderDTO> orderDTOPage = orderService.findAll(pageRequest);
        map.put("orderDTOPage",orderDTOPage);
        map.put("currentPage",page);
        map.put("size",size);
        return new ModelAndView("order/list",map);
    }
}
