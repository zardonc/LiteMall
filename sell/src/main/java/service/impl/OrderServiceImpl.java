package service.impl;

import dataobject.OrderDetail;
import dataobject.OrderMaster;
import dataobject.ProductInfo;
import dto.CartDTO;
import dto.OrderDTO;
import enums.OrderStatusEnum;
import enums.PayStatusEnum;
import enums.ResultEnum;
import exception.SellException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import repository.OrderDetailRepo;
import repository.OrderMasterRepo;
import service.OrderService;
import service.ProductService;
import utils.KeyUtil;
import utils.OrderMaster2OrderDTOconv;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailRepo orderDetailRepo;

    @Autowired
    private OrderMasterRepo orderMasterRepo;

    /**
     * 1.查询商品（数量、价格）
     * 2.汇总价格
     * 3.写入数据库(订单主表与详情表)
     * 4.扣库存
     */
    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        String orderId = KeyUtil.genUniqueKey();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);

        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            // 计算订单总价
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);
            // 入库(订单详情)
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetailRepo.save(orderDetail);
        }
        // 写入OrderMaster
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.PENDING.getCode());
        orderMasterRepo.save(orderMaster);

        // 扣库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e ->
                new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        // TODO 保证线程安全防止超卖
        productService.DescStock(cartDTOList);

        return orderDTO;
    }

    // 查询单个订单
    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterRepo.findById(orderId).get();
        if (orderMaster == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        // 查询订单详情
        List<OrderDetail> orderDetailList = orderDetailRepo.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new SellException((ResultEnum.ORDERDETAIL_NOT_EXIST));
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }

    // 查询订单列表
    @Override
    public Page<OrderDTO> findList(String buyerOpenId, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepo.findByBuyerOpenid(buyerOpenId, pageable);
        // 转为 List<OrderDTO>
        List<OrderDTO> orderDTOList =
                OrderMaster2OrderDTOconv.convert(orderMasterPage.getContent());
        // 转为Page<OrderDTO>
        return new PageImpl<OrderDTO>(orderDTOList, pageable, orderMasterPage.getTotalElements());
    }

    // 取消订单
    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();
        // 判段订单状态(非完结状态)
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            logger.error("【取消订单失败,状态异常】，orderId={},orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        // 修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCELLED.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster result = orderMasterRepo.save(orderMaster);
        if (result == null) {
            logger.error("【取消订单失败】,orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        // 返还库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e ->
                new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.incrStock(cartDTOList);
        // 已支付退款
        if (orderDTO.getOrderStatus().equals(PayStatusEnum.PAID.getCode())) {
            // TODO 退款
        }

        return orderDTO;
    }

    // 订单支付
    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();
        // 判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            logger.error("【订单无法支付,状态异常】，orderId={},orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        // 判段支付状态
        if (!orderDTO.getPayStatus().equals(PayStatusEnum.PENDING.getCode())) {
            logger.error("【订单无法支付,支付状态异常】，orderId={},payStatus={}", orderDTO.getOrderId(), orderDTO.getPayStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        // 修改支付状态
        orderDTO.setPayStatus(PayStatusEnum.PAID.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster result = orderMasterRepo.save(orderMaster);
        if (result == null) {
            logger.error("【订单支付完成，更新失败】,orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    // 订单完成
    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();
        // 判断状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            logger.error("【完结订单失败,状态异常】，orderId={},orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        // 修改状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster result = orderMasterRepo.save(orderMaster);
        if (result == null) {
            logger.error("【完结订单失败】,orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }
}
