package repository;

import dataobject.OrderDetail;
import dataobject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import sellapplication.sellApplication;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = sellApplication.class)
public class OrderDetailRepoTest {

    @Autowired
    private OrderDetailRepo orderDetailRepo;

    @Test
    public void saveTest() {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("1234");
        orderDetail.setOrderId("121212");
        orderDetail.setProductId("789");
        orderDetail.setProductIcon("http://bububu.png");
        orderDetail.setProductName("ROG2代");
        orderDetail.setProductPrice(new BigDecimal(10.235));
        orderDetail.setProductQuantity(99);

        OrderDetail order1 = orderDetailRepo.save(orderDetail);
        Assert.assertNotNull(order1);
    }

    @Test
    public void findByOrderId() {
        List<OrderDetail> orderDetails = orderDetailRepo.findByOrderId("121212");
        Assert.assertNotEquals(0,orderDetails.size());
    }
}