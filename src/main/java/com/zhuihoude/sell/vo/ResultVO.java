package com.zhuihoude.sell.vo;

import lombok.Data;

/* Http 请求返回的最外层对象 */
@Data
public class ResultVO<T> {

    /* 错误码. */
    private Integer code;

    /* 提示信息. */
    private  String msg;

    /* 返回对象. */
    private T data;
}
