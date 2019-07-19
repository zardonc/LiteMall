package service.impl;

import dataobject.ProductInfo;
import enums.ProductStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import repository.ProductInfoRepo;
import service.ProductService;

import java.util.List;

public class ProductInfoImpl implements ProductService {
    @Autowired
    private ProductInfoRepo productInfoRepo;

    @Override
    public ProductInfo findOne(String productId) {
        return productInfoRepo.findById(productId).get();
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoRepo.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return productInfoRepo.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return productInfoRepo.save(productInfo);
    }
}
