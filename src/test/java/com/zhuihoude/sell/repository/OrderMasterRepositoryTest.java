package com.zhuihoude.sell.repository;

import com.zhuihoude.sell.dataobject.OrderMaster;
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

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository repository;

    @Test
    public void saveTest () {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("1233");
        orderMaster.setBuyerName("qweqweqweqweqwmaijai麦加名字");
        orderMaster.setBuyerPhone("11");
        orderMaster.setBuyerAddress("美国");
        orderMaster.setBuyerOpenid("11");
        orderMaster.setOrderAmount(new BigDecimal(123.1));

        OrderMaster result = repository.save(orderMaster);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByBuyerOpenid() throws Exception{
        //PageRequest request = new PageRequest(0,10);
        PageRequest request = PageRequest.of(1,10);

        Page<OrderMaster> result = repository.findByBuyerOpenid("11",request);
        Assert.assertNotEquals(0,result.getTotalElements());
        log.info(result.getTotalElements()+"");
    }
}