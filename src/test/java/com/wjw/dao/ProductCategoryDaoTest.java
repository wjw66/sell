package com.wjw.dao;

import com.wjw.dataobject.ProductCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryDaoTest {

    @Autowired
    private ProductCategoryDao productCategoryDao;
    @Test
    public void findOneTest(){
        ProductCategory productCategory = productCategoryDao.findOne(1);
        System.out.println(productCategory.toString());
    }

    @Test
    public void saveTest(){

//        ProductCategory productCategoryDaoOne = productCategoryDao.findOne(2);
//        productCategoryDaoOne.setCategoryName("火腿蛋炒饭");
//        productCategoryDao.save(productCategoryDaoOne);
        /*
          使用空参构造设置
         */
        //ProductCategory productCategory = new ProductCategory();
        //productCategory.setCategoryType(3);
        //productCategoryDao.save(productCategory);
        /*
          使用有参构造
         */
        ProductCategory productCategory = new ProductCategory("必点类", 4);
        productCategoryDao.save(productCategory);
    }
    @Test
    public void findByCategoryTypeInTest(){
        List<Integer> list = Arrays.asList(2,3,4);
        List<ProductCategory> result = productCategoryDao.findByCategoryTypeIn(list);
        System.out.println(result);
    }
}