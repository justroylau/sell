package com.zhuihoude.sell.service;

import com.zhuihoude.sell.dataobject.ProductInfo;
import com.zhuihoude.sell.dto.ShoppingCartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    /** 查询一个商品. */
    ProductInfo getOne(String productId);

    /** 查询所有在架商品列表. */
    List<ProductInfo> findUpAll();

    /** 查询所有商品. */
    Page<ProductInfo> findAll(Pageable pageable);

    /** 添加商品. */
    ProductInfo save(ProductInfo productInfo);

    /** 加库存. */
    void increaseStock(List<ShoppingCartDTO> shoppingCartDTOList);

    /** 减库存 */
    void decreaseStock(List<ShoppingCartDTO> shoppingCartDTOList);
}
