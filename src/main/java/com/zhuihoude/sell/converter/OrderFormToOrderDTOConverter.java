package com.zhuihoude.sell.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhuihoude.sell.dataobject.OrderDetail;
import com.zhuihoude.sell.dto.OrderDTO;
import com.zhuihoude.sell.enums.ResultEnum;
import com.zhuihoude.sell.exception.SellException;
import com.zhuihoude.sell.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OrderFormToOrderDTOConverter {

    public static OrderDTO convert (OrderForm orderForm) {

        Gson gson = new Gson();

        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
            orderDetailList = gson.fromJson(orderForm.getItems(), new TypeToken<List<OrderDetail>>(){}.getType());
        }catch (Exception e) {
            log.error("【d对象转换】错误，String={}",orderForm.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }
}
