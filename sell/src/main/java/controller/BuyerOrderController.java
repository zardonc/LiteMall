package controller;

import VO.ResultVO;
import dto.OrderDTO;
import enums.ResultEnum;
import exception.SellException;
import form.OrderForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import service.BuyerService;
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
    @Autowired
    private BuyerService buyerService;
    // 创建订单
    @PostMapping("create")
    public ResultVO create(@Valid OrderForm orderForm,
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
    @GetMapping("list")
    public ResultVO queryList(@RequestParam("openid") String openid,
                              @RequestParam(value = "page",defaultValue = "0") Integer page,
                              @RequestParam(value = "size",defaultValue = "10") Integer size){
        if (StringUtils.isEmpty(openid)){
            log.error("【查询订单列表】openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        Pageable pageable = PageRequest.of(page,size);
        Page<OrderDTO> orderDTOPage = orderService.findList(openid,pageable);

        return ResultVOUtil.success(orderDTOPage.getContent());
    }
    // 订单详情
    @GetMapping("detail")
    public ResultVO<OrderDTO> queryDetail(@RequestParam("openid") String openid,
                                          @RequestParam("orderId") String orderId){
        OrderDTO orderDTO = buyerService.findOrderOne(openid,orderId);
        return ResultVOUtil.success(orderDTO);
    }

    // 取消订单
    @PostMapping("cancel")
    public ResultVO cancelOrder(@RequestParam("openid") String openid,
                                          @RequestParam("orderId") String orderId){
        buyerService.cancelOrder(openid,orderId);
        // 返回data为null
        return ResultVOUtil.success();
    }
}
