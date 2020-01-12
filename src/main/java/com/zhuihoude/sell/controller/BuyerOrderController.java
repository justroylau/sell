package com.zhuihoude.sell.controller;

import com.zhuihoude.sell.converter.OrderFormToOrderDTOConverter;
import com.zhuihoude.sell.dto.OrderDTO;
import com.zhuihoude.sell.enums.ResultEnum;
import com.zhuihoude.sell.exception.SellException;
import com.zhuihoude.sell.form.OrderForm;
import com.zhuihoude.sell.service.BuyerService;
import com.zhuihoude.sell.service.OrderService;
import com.zhuihoude.sell.utils.ResultVOUtil;
import com.zhuihoude.sell.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order/")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    BuyerService buyerService;

    //创建订单  //  /sell/buyer/order/create
    @PostMapping("/create")
    public ResultVO<Map<String ,String>> create(@Valid OrderForm orderForm, BindingResult bindingResult){

        if(bindingResult.hasErrors()) {
            log.error("【创建订单】参数不正确，orderForm = {}",orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage()); //?????
        }

        OrderDTO orderDTO = new OrderFormToOrderDTOConverter().convert(orderForm);
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【创建订单】购物车不能为空");
            throw new SellException( ResultEnum.SHOPPING_CAR_EMPTY);
        }

        OrderDTO createResult = orderService.create(orderDTO);
        Map<String,String> map = new HashMap<>();
        map.put("orderId",createResult.getOrderId());


        return ResultVOUtil.success(map);
    }

    //订单列表
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>>  list(@RequestParam("openid") String openid,@RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size ) {

        if (StringUtils.isEmpty(openid)) {
            log.error ("【查询订单列表】openid为空！");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        PageRequest request = PageRequest.of(page,size);
        Page<OrderDTO> orderServiceList = orderService.findList(openid, request);
        return ResultVOUtil.success(orderServiceList.getContent());
    }

    //订单详情
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid, @RequestParam("orderid") String orderId){
        OrderDTO orderDTO = buyerService.findOrderOne(openid, orderId);
        return ResultVOUtil.success(orderDTO);
    }


    //取消订单
    @PostMapping("/cancel")
    public ResultVO<Map<String,String>> cancel(@RequestParam("openid") String openid, @RequestParam("orderid") String orderId) {
        //返回取消的
        return ResultVOUtil.success(buyerService.cancelOrder(openid, orderId));
    }
}
