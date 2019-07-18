package service.impl;

import dataobject.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ProductCategoryRepo;
import service.CategoryService;

import java.util.List;

/**
 * 商品类目
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ProductCategoryRepo productCategoryRepo;

    @Override
    public ProductCategory findOne(Integer categoryId) {
        return productCategoryRepo.findById(categoryId).get();
    }

    @Override
    public List<ProductCategory> findAll() {
        return productCategoryRepo.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return productCategoryRepo.findByCategoryTypeIn(categoryTypeList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return productCategoryRepo.save(productCategory);
    }
}
