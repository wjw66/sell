package com.wjw.service;

import com.wjw.dataobject.ProductCategory;

import java.util.List;

public interface CategoryService {
    //后台管理端使用
    ProductCategory findOne(Integer categoryId);
    List<ProductCategory> findAll();
    //买家端使用
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
    //新增和更新都用save方法
    ProductCategory save(ProductCategory productCategory);
}
