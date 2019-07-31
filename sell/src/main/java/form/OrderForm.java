package form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 订单表单验证
 */
@Data
public class OrderForm {

    @NotEmpty(message = "姓名必填")
    private String name;

    @NotEmpty(message = "手机号必填")
    private String phone;

    @NotEmpty(message = "地址必填")
    private String address;

    @NotEmpty(message = "openid")
    private String openid;

    // 传入json格式
    @NotEmpty(message = "购物车")
    private String items;
}
