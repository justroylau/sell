package com.zhuihoude.sell.dataobject;

import com.zhuihoude.sell.enums.OrderStatusEnum;
import com.zhuihoude.sell.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

//@Table(name = "表名")
@Entity  //表明该类 为一个实体类,它默认对应数据库中的表名是 product_category
@DynamicUpdate  //动态更新，使得数据库时间正常更新
@Proxy(lazy = false)  //hibernate 延迟 懒加载 不加：当前类 - no Session
@EnableTransactionManagement
@Data  /* 不需要写get set 自动更新get set*/
public class OrderMaster {

    /** 订单Id. */
    @Id
    private String orderId;

    /** 买家名字Id. */
    private String buyerName;

    /** 买家手机号Id. */
    private String buyerPhone;

    /** 买家地址Id. */
    private String buyerAddress;

    /** 买家微信Openid. */
    private String buyerOpenid;

    /** 订单总金额. */
    private BigDecimal orderAmount;

    /** 订单状态,默认0新下单. */
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

    /** 支付状态，默认0未支付. */
    private Integer payStatus = PayStatusEnum.WAIT.getCode();

    private Date create_time;
    private Date update_time;

    /*@Transient  //忽略字段
    private List<OrderDetail> orderDetailList;*/
}
