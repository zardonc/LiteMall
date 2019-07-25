package service.impl;

import dataobject.OrderDetail;
import dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sellapplication.sellApplication;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = sellApplication.class)
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;

    private final String BUYER_OPENID = "101110";

    @Test
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
        log.info("创建订单 result={}",testOrder);
    }
}