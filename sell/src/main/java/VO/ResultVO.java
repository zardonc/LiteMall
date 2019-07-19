package VO;

import lombok.Data;

/**
 * http请求返回给前端的对象
 */
@Data
public class ResultVO<T> {
    // 错误码
    private Integer code;
    // 提示信息
    private String msg;
    // 返回的具体内容
    private T data;

}
