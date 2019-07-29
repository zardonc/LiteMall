package utils;

import dataobject.OrderMaster;
import dto.OrderDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 将OrderMaster转为OrderDTO
 */
public class OrderMaster2OrderDTOconv {
    public static OrderDTO converter(OrderMaster orderMaster){
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        return orderDTO;
    }
    public static List<OrderDTO> convert(List<OrderMaster> orderMasterList){
       return orderMasterList.stream()
               .map(e -> converter(e)).collect(Collectors.toList());
    }
}
