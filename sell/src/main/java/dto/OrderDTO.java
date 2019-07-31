package dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import dataobject.OrderDetail;
import lombok.Data;
import utils.Date2LongSerializer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
// 空字段不返回给前端
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {
    // 订单编号
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
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;
    // 更新时间
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;
    // 订单详情列表
    private List<OrderDetail> orderDetailList = new ArrayList<>();
}
