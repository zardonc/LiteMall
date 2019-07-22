package dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

// 订单详情表
@Data
@Entity
public class OrderDetail {
    @Id
    private String detailId;
    // 订单编号
    private String orderId;
    // 商品编号
    private String productId;
    // 品名
    private String productName;
    // 商品单价
    private BigDecimal productPrice;
    // 数量
    private Integer productQuantity;
    // 商品小图
    private String productIcon;
    // 创建时间
    private Date createTime;
    // 更新时间
    private Date updateTime;
}
