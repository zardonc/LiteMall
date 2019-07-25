package service;

import dataobject.ProductInfo;
import dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    ProductInfo findOne(String productId);
    /**
     * 查询所有在售商品
     */
    List<ProductInfo> findUpAll();
    /**
     * 分页查询所有商品
     * @param pageable
     * @return
     */
    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    // 加库存
    void incrStock(List<CartDTO> cartDTOList);
    // 减库存
    void DescStock(List<CartDTO> cartDTOList);
}
