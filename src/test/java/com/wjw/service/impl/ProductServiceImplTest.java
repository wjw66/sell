package com.wjw.service.impl;

import com.wjw.dataobject.ProductInfo;
import com.wjw.enums.ProductStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {
    @Autowired
    private ProductServiceImpl productService;

    @Test
    public void findOne() {
        ProductInfo productServiceOne = productService.findOne("9852e7d9-07ad-412c-b2c5-2973d1ed2f70");
        Assert.assertEquals("9852e7d9-07ad-412c-b2c5-2973d1ed2f70",productServiceOne.getProductId());
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> productServiceUpAll = productService.findUpAll();
        Assert.assertNotEquals(0,productServiceUpAll.size());
    }

    @Test
    public void findAll() {
        /**
         * PageRequest extends AbstractPageRequest
         * AbstractPageRequest implements Pageable
         * Pageable只是一个接口不是具体的类
         */
        PageRequest pageRequest = new PageRequest(0, 2);
        Page<ProductInfo> productServiceAll = productService.findAll(pageRequest);
        System.out.println(productServiceAll.getTotalElements());
    }

    @Test
    public void save() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId(UUID.randomUUID().toString().replace("-",""));
        productInfo.setProductName("蜜汁鸡翅");
        productInfo.setProductPrice(new BigDecimal(18));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("正宗的鸡翅!");
        productInfo.setProductIcon("www.baidu.com");
        productInfo.setCategoryType(2);
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        ProductInfo result = productService.save(productInfo);
        Assert.assertNotNull(result);
    }
}