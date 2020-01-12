package com.zhuihoude.sell.service.impl;

import com.zhuihoude.sell.dataobject.ProductInfo;
import com.zhuihoude.sell.enums.ProductStatusEnum;
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
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;

    @Test
    void getOne() throws Exception {
        ProductInfo productInfo = productService.getOne("121");
        log.info(productInfo.toString());
        Assert.assertEquals("121",productInfo.getProductId());
    }

    @Test
    void findUpAll() throws Exception {
        List<ProductInfo> productInfoList = productService.findUpAll();
        Assert.assertNotEquals(0,productInfoList.size());
    }

    @Test
    void findAll() throws Exception {
        //PageRequest request = new PageRequest(0,10);
        PageRequest request = PageRequest.of(0,10);
        Page<ProductInfo> productInfoPage = productService.findAll(request);
        System.out.println(productInfoPage.getTotalElements());
        Assert.assertNotEquals(0,productInfoPage.getTotalElements());
    }

    @Test
    void save() throws Exception {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("5");
        productInfo.setProductName("好吃的巧克力寄吧");
        productInfo.setProductPrice(new BigDecimal(3.2));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("大吉大利，今晚吃鸡吧～");
        productInfo.setProductIcon("https://www.zhuihoude.com/ji.jpg");
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        productInfo.setCategoryType(1);

        ProductInfo result = productService.save(productInfo);
        Assert.assertNotNull(result);
    }
}