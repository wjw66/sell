package com.wjw.service.impl;

import com.wjw.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {

    @Autowired
    private CategoryServiceImpl categoryService;
    @Test
    public void findOne() {
        ProductCategory categoryServiceOne = categoryService.findOne(2);
        System.out.println(categoryServiceOne);
        Assert.assertEquals(1,1);
    }

    @Test
    public void findAll() {
        List<ProductCategory> categoryServiceAll = categoryService.findAll();
    }

    @Test
    public void findByCategoryTypeIn() {
        List<ProductCategory> categoryTypeIn = categoryService.findByCategoryTypeIn(Arrays.asList(2, 3, 4));
        Assert.assertNotNull(categoryTypeIn);
        System.out.println(categoryTypeIn);
    }

    @Test
    public void save() {
    }
}