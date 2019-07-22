package enums;

import lombok.Getter;

@Getter
public enum PayStatusEnum {
    PENDING(0,"等待支付"),
    PAID(1,"支付完成"),
    ;
    private Integer code;
    private String msg;

    PayStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
