package dataobject;

import enums.OrderStatusEnum;
import enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@DynamicUpdate
public class OrderMaster {
    @Id
    private String orderId;
    // 买家姓名
    private String buyerName;
    // 买家电话
    private String buyerPhone;
    // 买家地址
    private String buyerAddress;
    // 买家微信id
    private String buyerOpenid;
    // 订单总额
    private BigDecimal orderAmount;
    // 订单状态,默认0新下单
    private Integer orderStatus= OrderStatusEnum.NEW.getCode();
    // 支付状态，默认0未支付
    private Integer payStatus= PayStatusEnum.PENDING.getCode();
    // 创建时间
    @Column(name = "create_time",insertable = false, updatable = false,columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createTime;
    // 更新时间
    @Column(name = "update_time",insertable = false, updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date updateTime;
}
