package com.zhuihoude.sell.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zhuihoude.sell.dataobject.OrderDetail;
import com.zhuihoude.sell.enums.OrderStatusEnum;
import com.zhuihoude.sell.enums.PayStatusEnum;
import com.zhuihoude.sell.utils.serializer.DataToLongSerializer;
import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
//@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
//@JsonInclude(JsonInclude.Include.NON_NULL) //空属性不会反回前端
public class OrderDTO {

    /** 订单Id. */
    private String orderId;

    /** 买家名字Id. */
    private String buyerName;

    /** 买家手机号Id. */
    private String buyerPhone;

    /** 买家地址Id. */
    private String buyerAddress;

    /** 买家微信Openid. */
    private String buyerOpenid;

    /** 订单总金额. */
    private BigDecimal orderAmount;

    /** 订单状态,默认0新下单. */
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

    /** 支付状态，默认0未支付. */
    private Integer payStatus = PayStatusEnum.WAIT.getCode();

    @JsonSerialize(using = DataToLongSerializer.class)
    private Date create_time;

    @JsonSerialize(using = DataToLongSerializer.class)
    private Date update_time;

    private List<OrderDetail> orderDetailList = new ArrayList<>();

}
