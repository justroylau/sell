package com.zhuihoude.sell.controller;

import com.zhuihoude.sell.dataobject.ProductCategory;
import com.zhuihoude.sell.dataobject.ProductInfo;
import com.zhuihoude.sell.service.ProductCategoryService;
import com.zhuihoude.sell.service.ProductService;
import com.zhuihoude.sell.utils.ResultVOUtil;
import com.zhuihoude.sell.vo.ProductInfoVO;
import com.zhuihoude.sell.vo.ProductCategoryVO;
import com.zhuihoude.sell.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buyer/product/")
public class BuyerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryService categoryService;

    @GetMapping("/list")
    public ResultVO list(){

         // 1。查询所有上架商品
        List<ProductInfo> productInfoList = productService.findUpAll();

         // 2。查询类目（一次性查询）
       /* List<Integer> categoryTypeList = new ArrayList<>();*/
        //传统方法
        /*for(ProductInfo productInfo : productInfoList) {
            categoryTypeList.add(productInfo.getCategoryType());
        }*/

        //精简方法(Java8,lambda)
        List<Integer> categoryTypeList =
                productInfoList.stream()
                        .map(e -> e.getCategoryType())
                        .collect(Collectors.toList());
        //查询对应类目
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTypeList);


         // 3。数据拼装
        //实际返回对象
        List<ProductCategoryVO> productCategoryVOList = new ArrayList<>();
        for(ProductCategory productCategory : productCategoryList) {

            ProductCategoryVO productCategoryVO = new ProductCategoryVO();
            productCategoryVO.setCategoryType(productCategory.getCategoryType());
            productCategoryVO.setCategoryName(productCategory.getCategoryName());

            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for(ProductInfo productInfo : productInfoList){
                if(productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    //productInfo.set!
                    BeanUtils.copyProperties(productInfo,productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productCategoryVO.setProductInfoVOList(productInfoVOList);
            productCategoryVOList.add(productCategoryVO);
        }

        return ResultVOUtil.success(productCategoryVOList);
    }

}
