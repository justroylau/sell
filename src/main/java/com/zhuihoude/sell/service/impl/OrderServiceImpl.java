package com.zhuihoude.sell.service.impl;

import com.zhuihoude.sell.converter.OrderMasterToOrderDTOConverter;
import com.zhuihoude.sell.dataobject.OrderDetail;
import com.zhuihoude.sell.dataobject.OrderMaster;
import com.zhuihoude.sell.dataobject.ProductInfo;
import com.zhuihoude.sell.dto.OrderDTO;
import com.zhuihoude.sell.dto.ShoppingCartDTO;
import com.zhuihoude.sell.enums.OrderStatusEnum;
import com.zhuihoude.sell.enums.PayStatusEnum;
import com.zhuihoude.sell.enums.ResultEnum;
import com.zhuihoude.sell.exception.SellException;
import com.zhuihoude.sell.repository.OrderDetailRepository;
import com.zhuihoude.sell.repository.OrderMasterRepository;
import com.zhuihoude.sell.service.OrderService;
import com.zhuihoude.sell.service.ProductService;
import com.zhuihoude.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductService productService;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderMasterRepository orderMasterRepository;

    /**
     * 创建订单.
     *
     * @param orderDTO
     */
    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        //订单ID 应该在创建的使唤就生成了（唯一的）
        String orderId = KeyUtil.genUniqueKey();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        //List<ShoppingCartDTO> shoppingCartDTOList = new ArrayList<>(); //统计库存

        //1. 查询商品（数量，价格）
        for(OrderDetail orderDetail :orderDTO.getOrderDetailList()) {
            ProductInfo productInfo = productService.getOne(orderDetail.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            //2. 计算总价
            orderAmount = productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity())).add( orderAmount);

            //订单详情入库
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            //补完整信息
            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetailRepository.save(orderDetail);

            //ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO(orderDetail.getProductId(),orderDetail.getProductQuantity());//统计库存
            //shoppingCartDTOList.add(shoppingCartDTO);//统计库存
        }

        //3. 写入订单数据库 (OrderMaser,OrderDetail)
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderAmount(orderAmount);
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO,orderMaster);


        log.info("OrderServiceImpl：this.写入订单数据库返回值："+orderMasterRepository.save(orderMaster));



        //4. 扣掉库存   ???到底发生了什么
        //挖个坑，库存问题超卖 加锁
        List<ShoppingCartDTO> shoppingCartDTOList = orderDTO.getOrderDetailList().stream().map(e -> new ShoppingCartDTO(e.getProductId(),e.getProductQuantity())).collect(Collectors.toList());

        productService.decreaseStock(shoppingCartDTOList);

        return orderDTO;
    }

    /**
     * 查询单个订单.
     *
     * @param orderId
     */
    @Override
    public OrderDTO getOne(String orderId) {

        OrderMaster orderMaster = orderMasterRepository.getOne(orderId);
        if (orderMaster == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if(CollectionUtils.isEmpty(orderDetailList)){
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }

    /**
     * 查询订单列表.
     *
     * @param buyerOpenid
     * @param pageable
     */
    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {

        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid,pageable);

        List<OrderDTO> orderDTOList = OrderMasterToOrderDTOConverter.convert(orderMasterPage.getContent());

        Page<OrderDTO> orderDTOPage = new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());

        return orderDTOPage;
    }

    /**
     * 取消订单.
     *
     * @param orderDTO
     */
    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {

        OrderMaster orderMaster = new OrderMaster();

        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【取消订单】状态不正确, orderID = {}, orderStatus = {}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);//修改完再拷贝
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(updateResult == null) {
            log.error("【取消订单】更新失败，orderMaster = {}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        //返还库存
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【取消订单】订单中无商品详情，orderDTP = {}",orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<ShoppingCartDTO> shoppingCartDTOList = orderDTO.getOrderDetailList().stream().map(e -> new ShoppingCartDTO(e.getProductId(),e.getProductQuantity())).collect(Collectors.toList());
        productService.increaseStock(shoppingCartDTOList);

        //if支付 退款
        if(orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
            //TODO
        }
        log.info("【取消订单】成功，getOrderId = {}",orderDTO.getOrderId());
        return orderDTO;
    }

    /**
     * 完结订单.
     *
     * @param orderDTO
     */
    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {

        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【完结订单】订单状态不正确，orderID = {},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);

        if(updateResult == null) {
            log.error("【完结订单】更新失败，orderMaster = {}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        log.info("【完结订单】成功，getOrderId = {}",updateResult.getOrderId());

        return orderDTO;
    }

    /**
     * 支付订单.
     *
     * @param orderDTO
     */
    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {

        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【支付订单】订单状态不正确，orderID = {},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //判断支付状态
        if(!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
            log.error("【支付订单】订单状态不正确，orderID = {},orderPayStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);

        if(updateResult == null) {
            log.error("【支付订单】更新失败，orderMaster = {}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        log.info("【支付订单】成功，getOrderId = {}",updateResult.getOrderId());

        return orderDTO;
    }

}
