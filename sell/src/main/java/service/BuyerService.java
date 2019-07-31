package service;

import dto.OrderDTO;

/**
 * 买家api接口所需逻辑
 */
public interface BuyerService {
    // 查询单个订单
    OrderDTO findOrderOne(String openid,String orderId);
    // 取消订单
    OrderDTO cancelOrder(String openid,String orderId);
}
