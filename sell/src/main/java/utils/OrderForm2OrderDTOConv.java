package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dataobject.OrderDetail;
import dto.OrderDTO;
import enums.ResultEnum;
import exception.SellException;
import form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;


@Slf4j
public class OrderForm2OrderDTOConv {
    public static OrderDTO convert(OrderForm orderForm){
        ObjectMapper objectMapper = new ObjectMapper();

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        try {
            // 传入Json格式: "[{ \"color\" : \"Black\", \"type\" : \"BMW\" }, { \"color\" : \"Red\", \"type\" : \"FIAT\" }]"
            List<OrderDetail> orderDetails = objectMapper.readValue(orderForm.getItems(),new TypeReference<List<OrderDetail>>(){});
            orderDTO.setOrderDetailList(orderDetails);
        } catch (IOException e) {
            log.error("【创建订单】订单参数错误，orderForm={}",orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        return orderDTO;
    }
}
