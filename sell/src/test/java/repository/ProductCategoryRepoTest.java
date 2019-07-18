package repository;

import dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import sellapplication.sellApplication;

import java.util.Arrays;
import java.util.List;

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
    @Transactional // 测试后完全回滚
    public void saveTest() {
        // 查询条目
        ProductCategory productCategory = productCategoryRepo.findById(1).get();
        // TODO 验证权限后更新
        productCategory.setCategoryName("测试类目1");
        productCategoryRepo.save(productCategory);

        ProductCategory productCategory2 = new ProductCategory();
        productCategory2.setCategoryId(2);
        productCategory2.setCategoryName("bb");
        productCategory2.setCategoryType(3);
        // save 更新及添加
        ProductCategory result = productCategoryRepo.save(productCategory2);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByCategoryTypeInTest() {
        List<Integer> list = Arrays.asList(2,3,4);
        List<ProductCategory> result = productCategoryRepo.findByCategoryTypeIn(list);
        Assert.assertNotEquals(0,result.size());
    }
}