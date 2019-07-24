package repository;

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

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = sellApplication.class)
public class OrderMasterRepoTest {
    @Autowired
    private OrderMasterRepo orderMasterRepo;

    private final String OPENID = "56789";

    @Test
    public void saveTest(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("1235");
        orderMaster.setBuyerName("cyy");
        orderMaster.setBuyerPhone("137665678");
        orderMaster.setBuyerAddress("人民广场");
        orderMaster.setBuyerOpenid(OPENID);
        orderMaster.setOrderAmount(new BigDecimal(9.25));
        OrderMaster order1 = orderMasterRepo.save(orderMaster);
        Assert.assertNotNull(order1);
    }

    @Test
    public void findByBuyerOpenid() {
        Pageable pageable = PageRequest.of(0,1);
        Page<OrderMaster> order2 = orderMasterRepo.findByBuyerOpenid(OPENID,pageable);
//        System.out.println(order2.getTotalElements());
        Assert.assertNotEquals(0,order2.getTotalElements());
    }
}