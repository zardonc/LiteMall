package repository;

import dataobject.ProductCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sellapplication.sellApplication;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = sellApplication.class)
public class ProductCategoryRepoTest {
    @Autowired
    private ProductCategoryRepo productCategoryRepo;

    @Test
    public void findOneTest(){
        ProductCategory productCategory = productCategoryRepo.findById(1).get();
        System.out.println(productCategory.toString());
    }

    @Test
    public void saveTest() {
        ProductCategory productCategory2 = new ProductCategory();
        productCategory2.setCategoryName("热销");
        productCategory2.setCategoryType(3);
        productCategoryRepo.save(productCategory2);
    }
}