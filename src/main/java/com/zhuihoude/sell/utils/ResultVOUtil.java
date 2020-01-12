package com.zhuihoude.sell.utils;

import com.zhuihoude.sell.vo.ResultVO;

public class ResultVOUtil {

    public static ResultVO success(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(1);
        resultVO.setMsg("成功");
        resultVO.setData(object);
        return resultVO;
    }

    public static ResultVO success() {
        return success(null);
    }

    public static ResultVO error (Integer code,String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }

    public static ResultVO error (String msg) {
        return error(0,msg);
    }
}
