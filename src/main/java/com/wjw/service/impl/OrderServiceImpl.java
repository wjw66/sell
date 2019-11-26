package com.wjw.service.impl;

import com.wjw.converter.OrderMaster2OrderDTO;
import com.wjw.dao.OrderDetailDao;
import com.wjw.dao.OrderMasterDao;
import com.wjw.dataobject.OrderDetail;
import com.wjw.dataobject.OrderMaster;
import com.wjw.dataobject.ProductInfo;
import com.wjw.dto.CartDTO;
import com.wjw.dto.OrderDTO;
import com.wjw.enums.OrderStatusEnum;
import com.wjw.enums.PayStatusEnum;
import com.wjw.enums.ResultEnum;
import com.wjw.exception.SellException;
import com.wjw.service.OrderService;
import com.wjw.service.PayService;
import com.wjw.service.ProductService;
import com.wjw.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDetailDao orderDetailDao;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderMasterDao orderMasterDao;
    @Autowired
    private PayService payService;

    String orderId = KeyUtil.genUnique();

    /**

     */
    /**
     * 创建订单的业务逻辑的步骤
     * 1.获取用户传入的数据（前端点击提交后传入的数据，具体字段看api）
     * orderDTO为前后端交互的bean之一
     * 2.查询用户下单商品的库存合价格
     * 3.计算总价
     * 4.将订单写入到相关的数据库中（orderMaster,orderDetail）
     * 5.减库存
     *
     * @param orderDTO
     * @return
     */
    @Override
    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {
        String orderId = KeyUtil.genUnique();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        //List<CartDTO> cartDTOList = new ArrayList<>();

        //1.查询商品(数量,价格)
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            //2.计算订单总价(BigAmount的方式和int的不同)
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);

            //3.订单详情orderDetail入库
            /* 不能这样写,因为订单ID必须在订单创建的时候就生成,而不是入库的时候再生成 */
            //orderDetail.setOrderId(KeyUtil.genUnique());
            BeanUtils.copyProperties(productInfo, orderDetail);//属性值为null也会被拷贝
            orderDetail.setOrderId(orderId);
            orderDetail.setDetailId(KeyUtil.genUnique());
            orderDetailDao.save(orderDetail);

            /* 扣库存逻辑,通常写法 */
            //CartDTO cartDTO = new CartDTO(orderDetail.getProductId(),orderDetail.getProductQuantity());
            //cartDTOList.add(cartDTO);
        }

        //4.订单orderMaster入库
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterDao.save(orderMaster);

        //5.扣库存
        //lambda表达式写法
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.decreaseStock(cartDTOList);
        return orderDTO;

    }

    /**
     * 查询单个订单
     *
     * @param orderId
     * @return
     */
    @Override
    public OrderDTO findOne(String orderId) {
        //查询订单主表
        OrderMaster ordermaster = orderMasterDao.findOne(orderId);
        if (ordermaster == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //查询订单详情
        List<OrderDetail> orderDetailList = orderDetailDao.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(ordermaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    /**
     * 分页查询订单列表
     *
     * @param buyerOpenid
     * @param pageable
     * @return
     */
    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterDao.findByBuyerOpenid(buyerOpenid, pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTO.convert(orderMasterPage.getContent());
        //page百度对象,orderMasterPage.getTotalElements()是干什么的,orderMasterPage.getContent()是干什么的
        return new PageImpl<OrderDTO>(orderDTOList, pageable, orderMasterPage.getTotalElements());
    }


    /**
     * 取消订单
     * 不能直接删除订单,只能修改订单的状态
     * 但是不能直接修改,必须先判断订单的状态,比如已完结的订单,已接单派送的订单
     * 不能直接修改其状态
     *
     * @param orderDTO
     * @return
     */
    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();
        //1.先判断订单状态
        if (!OrderStatusEnum.NEW.getCode().equals(orderDTO.getOrderStatus())) {
            log.error("【取消订单】订单状态不正确，orderId={},orderStatus={}",
                    orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //2.修改订单状态，并update到数据库
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);//记一次坑!设置完状态后再进行拷贝!
        OrderMaster updateResult = orderMasterDao.save(orderMaster);
        //判断返回得结果是否为空，若是则更新失败，通常不可能
        if (updateResult == null) {
            log.error("【订单取消】更新失败，orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FALL);
        }
        //3.修改完后返还库存
        //考虑代码健壮性，再次判断传入的订单
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【取消订单】订单中无商品详情，orderDTO={}", orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.increaseStock(cartDTOList);
        //若已支付则需要退款
        if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
            payService.refund(orderDTO);
        }
        return orderDTO;
    }

    /**
     * 完结订单
     *
     * @param orderDTO
     * @return
     */
    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        //1.判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【完结订单】订单状态不正确，orderId={},orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //2.修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISH.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster saveResult = orderMasterDao.save(orderMaster);
        if (saveResult == null) {
            log.error("【完结订单】更新失败,orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FALL);
        }
        return orderDTO;
    }

    /**
     * 支付订单
     * @param orderDTO
     * @return
     */
    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        //1.判断订单状态，新订单
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【订单支付】订单状态不正确，orderId={},orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //2.判断支付状态
        if (!PayStatusEnum.WAIT.getCode().equals(orderDTO.getPayStatus())) {
            log.error("【订单支付】订单支付状态不正确,orderId={},payStatus={}", orderDTO.getOrderId(), orderDTO.getPayStatus());
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        //3.修改支付状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster PaySaveResult = orderMasterDao.save(orderMaster);
        if (PaySaveResult == null) {
            log.error("【订单支付】更新失败,orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FALL);
        }
        return orderDTO;
    }

    /**
     * 卖家端查询所有订单
     * @param pageable 分页参数
     * @return 查询结果
     */
    @Override
    public Page<OrderDTO> findAll(Pageable pageable) {
        Page<OrderMaster> orderMasterAll = orderMasterDao.findAll(pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTO.convert(orderMasterAll.getContent());
        //page分页对象,orderMasterPage.getTotalElements()是干什么的,orderMasterPage.getContent()是干什么的
        return new PageImpl<>(orderDTOList, pageable, orderMasterAll.getTotalElements());
    }
}
