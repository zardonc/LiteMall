package service.impl;

import dataobject.OrderDetail;
import dto.OrderDTO;
import enums.OrderStatusEnum;
import enums.PayStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import sellapplication.sellApplication;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = sellApplication.class)
public class OrderServiceImplTest {

    private Logger logger = LoggerFactory.getLogger(OrderServiceImplTest.class);

    @Autowired
    private OrderServiceImpl orderService;

    private final String BUYER_OPENID = "101110";

    private final String ORDER_ID = "1564019879899248034";

    @Test
    @Transactional
    public void create() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("simpson");
        orderDTO.setBuyerAddress("上海");
        orderDTO.setBuyerPhone("13712345678");
        orderDTO.setBuyerOpenid(BUYER_OPENID);

        // 购物车
        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail order1 = new OrderDetail();
        order1.setProductId("123");
        order1.setProductQuantity(10);
        orderDetailList.add(order1);

        orderDTO.setOrderDetailList(orderDetailList);

        OrderDTO testOrder = orderService.create(orderDTO);
        logger.info("创建订单 result={}",testOrder);
    }

    @Test
    public void findOne() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        logger.info("查询单个订单 订单={}",orderDTO);
        Assert.assertEquals(ORDER_ID,orderDTO.getOrderId());
    }

    @Test
    public void findList() {
        Pageable pageable =  PageRequest.of(0,2);
        Page<OrderDTO> orderDTOPage = orderService.findList(BUYER_OPENID,pageable);
        Assert.assertNotEquals(0,orderDTOPage.getTotalElements());
    }

    @Test
    @Transactional
    public void cancel() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        orderService.cancel(orderDTO);
        Assert.assertEquals(OrderStatusEnum.CANCELLED.getCode(),orderDTO.getOrderStatus());
    }

    @Test
    @Transactional
    public void finish() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        orderService.finish(orderDTO);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(),orderDTO.getOrderStatus());
    }

    @Test
    @Transactional
    public void testPay() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        orderService.paid(orderDTO);
        Assert.assertEquals(PayStatusEnum.PAID.getCode(),orderDTO.getPayStatus());
    }
}