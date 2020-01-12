package com.zhuihoude.sell.service;

import com.zhuihoude.sell.dataobject.ProductCategory;

import java.util.List;

public interface ProductCategoryService {

    /** 查询一个商品类目. */
    ProductCategory getOne (Integer categoryId);

    /** 查询所有类目. */
    List<ProductCategory> findALl();

    /** 按照类目编号查询. */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> CategoryTypeList);

    /** 保存类目. */
    ProductCategory save(ProductCategory productCategory);
}
