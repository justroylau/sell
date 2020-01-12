package com.zhuihoude.sell.repository;

import com.zhuihoude.sell.dataobject.ProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public void findOneTest()  throws Exception{
        ProductCategory productCategory = repository.getOne(2);
        System.out.println(productCategory.toString());
    }

    @Test
    @Transactional //回滚
    public void saveTest() throws Exception{
        ProductCategory productCategory =
                new ProductCategory("类目1",1);

        ProductCategory result = repository.save(productCategory);
        Assert.assertNotNull(result);
        //Assert.assertNotEquals(null,result);
    }

    @Test
    public void updateTest() throws Exception{
        ProductCategory productCategory = repository.getOne(118);
        productCategory.setCategoryName("粥类");
        repository.save(productCategory);
    }

    @Test
    public void findByCategoryTypeInTest() throws Exception{
        List<Integer> list = Arrays.asList(1,3,5,6);
        List<ProductCategory> result = repository.findByCategoryTypeIn(list);
        log.info(result.toString());
        Assert.assertNotNull(result);
    }

}