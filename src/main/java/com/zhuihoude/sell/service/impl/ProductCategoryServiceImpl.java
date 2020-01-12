package com.zhuihoude.sell.service.impl;

import com.zhuihoude.sell.dataobject.ProductCategory;
import com.zhuihoude.sell.repository.ProductCategoryRepository;
import com.zhuihoude.sell.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryRepository repository;

    @Override
    public ProductCategory getOne(Integer categoryId) {
        return repository.getOne(categoryId);
    }

    @Override
    public List<ProductCategory> findALl() {
        return repository.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return repository.findByCategoryTypeIn(categoryTypeList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return repository.save(productCategory);
    }
}
