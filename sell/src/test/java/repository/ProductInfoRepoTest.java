package repository;

import dataobject.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sellapplication.sellApplication;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = sellApplication.class)
public class ProductInfoRepoTest {

    @Autowired
    private ProductInfoRepo infoRepo;

    @Test
    public void saveTest() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123");
        productInfo.setProductName("另请高明1代");
        productInfo.setProductPrice(new BigDecimal(5.5));
        productInfo.setProductStock(99);
        productInfo.setProductDescription("图样图森破");
        productInfo.setProductIcon("http://xxxx.jpg");
        productInfo.setProductStatus(0);
        productInfo.setCategoryType(2);

        ProductInfo item = infoRepo.save(productInfo);

        Assert.assertNotNull(item);
    }

    @Test
    public void findByProductStatus() {
        List<ProductInfo> productInfoList = infoRepo.findByProductStatus(0);
        Assert.assertNotEquals(0,productInfoList.size());
    }
}