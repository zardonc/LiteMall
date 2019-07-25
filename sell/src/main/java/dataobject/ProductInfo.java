package dataobject;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
public class ProductInfo {
    @Id
    private String productId;
    // 品名
    private String productName;
    // 单价
    private BigDecimal productPrice;
    // 库存数量
    private Integer productStock;
    // 描述
    private String productDescription;
    // 商品小图
    private String productIcon;
    // 状态,0在售1下架
    private Integer productStatus;
    // 类目
    private Integer categoryType;
}
