package com.zhuihoude.sell;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: RoyLau
 * @Despriction: slf4j首测
 * @Created Date :14:18 2019/11/19
 * @Modify By:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
//@data
public class LoggerTest {

    //private final Logger logger = LoggerFactory.getLogger(LoggerTest.class);
    //install plugins - lombok

    @Test
    public void test(){

        String name = "Test Name";
        String password = "Test Password";
        log.error("Error");  //40
        log.warn("Warn");    //30
        log.info("Info");    //20
        log.debug("Debug");  //10
        log.trace("Trace");  //0

        log.info("name:{}; password:{}",name,password);    //20

    }
}
