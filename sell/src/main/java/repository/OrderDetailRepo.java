package repository;

import dataobject.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepo extends JpaRepository<OrderDetail,String> {
    // 根据订单主记录(master)查询详情
    List<OrderDetail> findByOrderId(String orderId);
}
