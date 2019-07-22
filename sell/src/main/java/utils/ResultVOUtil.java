package utils;

import VO.ResultVO;

public class ResultVOUtil {

    public static ResultVO success(Object object){
        ResultVO resultVo = new ResultVO();
        resultVo.setData(object);
        resultVo.setCode(0);
        resultVo.setMsg("success");
        return resultVo;
    }

    public static ResultVO success(){
        return success(null);
    }

    public static ResultVO error(Integer code,String msg){
        ResultVO resultVo = new ResultVO();
        resultVo.setCode(code);
        resultVo.setMsg(msg);
        return resultVo;
    }
}
