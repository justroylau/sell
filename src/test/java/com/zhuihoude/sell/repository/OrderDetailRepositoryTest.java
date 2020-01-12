package com.zhuihoude.sell.repository;

import com.zhuihoude.sell.dataobject.OrderDetail;
import com.zhuihoude.sell.dataobject.OrderMaster;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository repository;

    @Test
    public void saveTest () {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("123");
        orderDetail.setOrderId("11");
        orderDetail.setProductIcon("https://zhuihou.com");
        orderDetail.setProductId("33");
        orderDetail.setProductName("能吃鸡的粥");
        orderDetail.setProductPrice(new BigDecimal(1.3));
        orderDetail.setProductQuantity(2);

        OrderDetail result = repository.save(orderDetail);
        Assert.assertNotNull(result);
    }

    @Test
    void findByOrderId() throws Exception{
        List<OrderDetail> result = repository.findByOrderId("11");
        Assert.assertNotEquals(0,result.size());

    }
}