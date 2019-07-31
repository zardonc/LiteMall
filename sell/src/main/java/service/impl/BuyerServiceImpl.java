package service.impl;

import dto.OrderDTO;
import enums.ResultEnum;
import exception.SellException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.BuyerService;
import service.OrderService;

@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private OrderService orderService;

    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {
        return checkOwner(openid, orderId);
    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {
        OrderDTO orderDTO = checkOwner(openid, orderId);
        if(orderDTO==null){
            log.error("【取消订单】订单不存在,orderId={}",orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        return orderService.cancel(orderDTO);
    }

    private OrderDTO checkOwner(String openid,String orderId){
        OrderDTO orderDTO = orderService.findOne(openid);
        if (orderDTO==null){
            return null;
        }
        // 判段是否为本人订单
        if(!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)){
            log.error("【查询订单】订单openid不一致, openid={},orderId={}",openid,orderId);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderDTO;
    }
}
