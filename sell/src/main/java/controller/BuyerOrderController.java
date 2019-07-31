package controller;

import VO.ResultVO;
import dto.OrderDTO;
import enums.ResultEnum;
import exception.SellException;
import form.OrderForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.OrderService;
import utils.OrderForm2OrderDTOConv;
import utils.ResultVOUtil;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 订单api接口
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {
    @Autowired
    private OrderService orderService;
    // 创建订单
    @PostMapping("create")
    public ResultVO<Map<String,String>> create(@Valid OrderForm orderForm,
                                               BindingResult bindingResult) {
        // 判段表单校验后是否错误
        if(bindingResult.hasErrors()){
            log.error("【创建订单】参数错误，orderForm={}",orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDTO orderDTO = OrderForm2OrderDTOConv.convert(orderForm);

        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【创建订单】购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY_ERROR);
        }
        OrderDTO newOrder = orderService.create(orderDTO);

        Map<String,String> map = new HashMap<>();
        map.put("orderId",newOrder.getOrderId());

        return ResultVOUtil.success(map);
    }
    // 订单列表

    // 订单详情

    // 取消订单
}
