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
    public void findOneTest() {
        ProductCategory productCategory = productCategoryRepo.findById(1).get();
        System.out.println(productCategory.toString());
    }

    @Test
    public void saveTest() {
        // 查询条目
        ProductCategory productCategory = productCategoryRepo.findById(1).get();
        // TODO 验证权限后更新
        productCategory.setCategoryName("测试类目1");
        productCategoryRepo.save(productCategory);

        ProductCategory productCategory2 = new ProductCategory();
        productCategory2.setCategoryId(2);
        productCategory2.setCategoryName("女生热销");
        productCategory2.setCategoryType(3);
        // save 更新及添加
        productCategoryRepo.save(productCategory2);
    }
}