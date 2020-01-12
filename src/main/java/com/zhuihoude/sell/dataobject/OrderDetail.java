package com.zhuihoude.sell.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

//@Table(name = "表名")
@Entity  //表明该类 为一个实体类,它默认对应数据库中的表名是 product_category
@DynamicUpdate  //动态更新，使得数据库时间正常更新
@Proxy(lazy = false)  //hibernate 延迟 懒加载 不加：当前类 - no Session
@EnableTransactionManagement
@Data  /* 不需要写get set 自动更新get set*/
public class OrderDetail {


    @Id
    private String detailId;

    /** 订单id. */
    private String orderId;

    /** 商品ID. */
    private String  productId;

    /** 商品名称. */
    private String productName;

    /** 商品单价. */
    private BigDecimal productPrice;

    /** 商品数量. */
    private Integer productQuantity;

    /** 商品小图. */
    private String productIcon;

    private Date create_time;
    private Date update_time;
}
