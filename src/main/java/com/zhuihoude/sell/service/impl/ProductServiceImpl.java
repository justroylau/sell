package com.zhuihoude.sell.service.impl;

import com.mysql.cj.protocol.x.XProtocolDecoder;
import com.zhuihoude.sell.dataobject.ProductInfo;
import com.zhuihoude.sell.dto.ShoppingCartDTO;
import com.zhuihoude.sell.enums.ProductStatusEnum;
import com.zhuihoude.sell.enums.ResultEnum;
import com.zhuihoude.sell.exception.SellException;
import com.zhuihoude.sell.repository.ProductInfoRepository;
import com.zhuihoude.sell.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository repository;

    @Override
    public ProductInfo getOne(String productId) {
        return repository.getOne(productId);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    @Override
    @Transactional
    public void increaseStock(List<ShoppingCartDTO> shoppingCartDTOList) {
        for(ShoppingCartDTO shoppingCartDTO : shoppingCartDTOList) {
            ProductInfo productInfo = repository.getOne(shoppingCartDTO.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock() + shoppingCartDTO.getProductQuantity();
            productInfo.setProductStock(result);
            repository.save(productInfo);
        }
    }

    @Override
    @Transactional
    public void decreaseStock(List<ShoppingCartDTO> shoppingCartDTOList) {
        for (ShoppingCartDTO shoppingCartDTO : shoppingCartDTOList){
            ProductInfo productInfo = repository.getOne(shoppingCartDTO.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock() - shoppingCartDTO.getProductQuantity();
            if(result <0) {
                log.error("当前剩余库存={},当前请求数量={}",productInfo.getProductStock(),shoppingCartDTO.getProductQuantity());
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);

            repository.save(productInfo);
        }
    }
}
