package com.zhuihoude.sell.service.impl;

import com.zhuihoude.sell.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ProductCategoryServiceImplTest {

    @Autowired
    private ProductCategoryServiceImpl productCategoryService;

    @Test
    void findOne() throws Exception {
        ProductCategory productCategory = productCategoryService.getOne(1);
        Assert.assertEquals(new Integer(1), productCategory.getCategoryId());

    }

    @Test
    void findALl() throws Exception {
        List<ProductCategory> productCategoryList = productCategoryService.findALl();
        Assert.assertNotEquals(0,productCategoryList.size());
    }

    @Test
    void findByCategoryTypeIn() throws Exception {
        List<ProductCategory>  productCategoryList = productCategoryService.findByCategoryTypeIn(Arrays.asList(1,3,5,6,7));
        Assert.assertNotEquals(0,productCategoryList.size());
    }

    @Test
    void save() throws Exception {
        ProductCategory productCategory = new ProductCategory("Name", 1032);
        Assert.assertNotNull(productCategory);
    }
}