package com.zhuihoude.sell.repository;

import com.zhuihoude.sell.dataobject.ProductCategory;
import com.zhuihoude.sell.dataobject.ProductInfo;
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
class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository repository;

    @Test
    public void saveTest() throws Exception {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123");
        productInfo.setProductName("皮蛋粥");
        productInfo.setProductPrice(new BigDecimal(3.2));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("真TMD好喝");
        productInfo.setProductIcon("https://www.zhuihoude.com/ji.jpg");
        productInfo.setProductStatus(1);
        productInfo.setCategoryType(1);

        ProductInfo result = repository.save(productInfo);
        Assert.assertNotNull(result);
    }

    @Test
    void findByProductStatus() throws Exception{

        List<ProductInfo> productInfoList = repository.findByProductStatus(1);
        Assert.assertNotEquals(0,productInfoList.size());
    }
}