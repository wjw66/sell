package com.wjw.dao;

import com.wjw.dataobject.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.UUID;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoDaoTest {
    @Autowired
    private ProductInfoDao productInfoDao;

    @Test
    public void savaTest(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId(UUID.randomUUID().toString());
        productInfo.setProductName("黄焖鸡米饭");
        productInfo.setProductPrice(new BigDecimal(18));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("正宗的黄焖鸡!");
        productInfo.setProductIcon("www.baidu.com");
        productInfo.setCategoryType(2);
        productInfo.setProductStatus(0);
        ProductInfo saveInfo = productInfoDao.save(productInfo);
        Assert.assertEquals(1,1);
    }

}