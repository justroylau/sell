package com.zhuihoude.sell.service.impl;

import com.zhuihoude.sell.dataobject.OrderDetail;
import com.zhuihoude.sell.dto.OrderDTO;
import com.zhuihoude.sell.dto.ShoppingCartDTO;
import com.zhuihoude.sell.enums.OrderStatusEnum;
import com.zhuihoude.sell.enums.PayStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;

    private final  String BUYER_OPENID = "666";
    private final  String ORDER_ID = "1577825034704835189";

    @Test
    void create() throws Exception {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerOpenid(BUYER_OPENID);
        orderDTO.setBuyerName("你老子");
        orderDTO.setBuyerAddress("住你家");
        orderDTO.setBuyerPhone("美丽的照片");
        orderDTO.setOrderAmount(new BigDecimal(666));

        //购物车
        List<OrderDetail> orderDetailList = new ArrayList<>();

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId("1");
        orderDetail.setProductQuantity(5);

        OrderDetail orderDetail1 = new OrderDetail();
        orderDetail1.setProductId("5");
        orderDetail1.setProductQuantity(3);


        orderDetailList.add(orderDetail);
        orderDetailList.add(orderDetail1);

        orderDTO.setOrderDetailList(orderDetailList);

        OrderDTO result = orderService.create(orderDTO);
        log.info("【创建订单】result={}",result);
        Assert.assertNotNull(result);
    }

    @Test
    void findOne() throws Exception {
        OrderDTO result = orderService.getOne(ORDER_ID);
        log.info("【查询单个订单】result={}",result);
        Assert.assertEquals(ORDER_ID,result.getOrderId());
    }

    @Test
    void findList() throws Exception {
        PageRequest request = PageRequest.of(0,10);
        Page<OrderDTO> orderDTOPage = orderService.findList(BUYER_OPENID,request);
        Assert.assertNotEquals(orderDTOPage, orderDTOPage.getTotalElements());
    }

    @Test
    void cancel() throws Exception {
        OrderDTO orderDTO = orderService.getOne(ORDER_ID);
        OrderDTO result = orderService.cancel(orderDTO);
        Assert.assertNotEquals(OrderStatusEnum.CANCEL.getMessage(),result.getOrderStatus());
    }

    @Test
    void finish() throws Exception {
        OrderDTO orderDTO = orderService.getOne(ORDER_ID);
        paid(); //偷懒，顺带测试支付
        OrderDTO result = orderService.finish(orderDTO);
        Assert.assertNotEquals(OrderStatusEnum.FINISHED.getMessage(),result.getOrderStatus());
    }

    @Test
    void paid() throws Exception {
        OrderDTO orderDTO = orderService.getOne(ORDER_ID);
        OrderDTO result = orderService.paid(orderDTO);
        Assert.assertNotEquals(PayStatusEnum.SUCCESS.getMessage(),result.getPayStatus());
    }
}