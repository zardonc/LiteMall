package service.impl;

import dataobject.ProductInfo;
import enums.ProductStatusEnum;
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
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = sellApplication.class)
public class ProductInfoServiceImplTest {

    @Autowired
    private ProductInfoServiceImpl productInfoService;

    @Test
    public void findOne() {
        ProductInfo pro1 = productInfoService.findOne("123");
        Assert.assertEquals("123",pro1.getProductId());
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> productInfoList = productInfoService.findUpAll();
        Assert.assertNotEquals(0,productInfoList.size());
    }

    @Test
    public void findAll() {
        Pageable pageable =  PageRequest.of(0,2);
        Page<ProductInfo> infoPage = productInfoService.findAll(pageable);
//        System.out.println(infoPage.getTotalElements());
        Assert.assertNotNull(infoPage);
    }

    @Test
    public void save() {
        ProductInfo pro2 = new ProductInfo();
        pro2.setProductId("124");
        pro2.setProductName("产品名2代");
        pro2.setProductPrice(new BigDecimal(6.65));
        pro2.setProductStock(99);
        pro2.setProductDescription("图样图森破");
        pro2.setProductIcon("http://xxxxX.png");
        pro2.setProductStatus(ProductStatusEnum.UP.getCode());
        pro2.setCategoryType(1);
        ProductInfo ans = productInfoService.save(pro2);
        Assert.assertNotNull(ans);
    }
}