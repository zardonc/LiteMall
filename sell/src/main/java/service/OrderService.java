package service;

import dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    // 创建订单
    OrderDTO create(OrderDTO orderDTO);
    // 单个查询
    OrderDTO findOne(String orderId);
    // 列表查询订单
    Page<OrderDTO> findList(String buyerOpenId, Pageable pageable);
    // 取消订单
    OrderDTO cancel(OrderDTO orderDTO);
    // 支付
    OrderDTO paid(OrderDTO orderDTO);
    // 完成
    OrderDTO finish(OrderDTO orderDTO);

}
