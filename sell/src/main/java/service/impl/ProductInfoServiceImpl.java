package service.impl;

import dataobject.ProductInfo;
import dto.CartDTO;
import enums.ProductStatusEnum;
import enums.ResultEnum;
import exception.SellException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.ProductInfoRepo;
import service.ProductService;

import java.util.List;

@Service
public class ProductInfoServiceImpl implements ProductService {
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

    @Override
    public void incrStock(List<CartDTO> cartDTOList) {

    }

    @Override
    @Transactional
    public void DescStock(List<CartDTO> cartDTOList) {

        for (CartDTO cartDTO:cartDTOList) {
            ProductInfo productInfo = productInfoRepo.findById(cartDTO.getProductId()).get();
            if(productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if(result < 0) {
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }

            productInfo.setProductStock(result);

            productInfoRepo.save(productInfo);
        }
    }
}
