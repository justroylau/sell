package com.zhuihoude.sell.service.impl;

import com.zhuihoude.sell.dto.OrderDTO;
import com.zhuihoude.sell.enums.ResultEnum;
import com.zhuihoude.sell.exception.SellException;
import com.zhuihoude.sell.service.BuyerService;
import com.zhuihoude.sell.service.OrderService;
import com.zhuihoude.sell.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    OrderService orderService;

    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {
        return checkOrderOwner(openid, orderId);
    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {
        OrderDTO orderDTO = checkOrderOwner(openid, orderId);
        if (orderDTO == null) {
            log.error("【取消订单】，查不到该订单，orderID={}",orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        return orderService.cancel(orderDTO);
    }

    private OrderDTO checkOrderOwner (String openid, String orderId) {

        OrderDTO orderDTO = orderService.getOne(orderId);
        if (orderDTO == null ){
            return null;
        }
        //判断是否是当前用户的订单
        if (!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid) ) {
            log.error("【查询订单】查询的订单非当前用户,openid = {},orderId = {}",openid,orderId);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderDTO;
    }
}
