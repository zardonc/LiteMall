package dto;

import dataobject.OrderDetail;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderDTO {
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
    private Integer orderStatus;
    // 支付状态，默认0未支付
    private Integer payStatus;
    // 创建时间
    private Date createTime;
    // 更新时间
    private Date updateTime;
    // 订单详情列表
    private List<OrderDetail> orderDetailList;
}
