package com.wjw.dao;

import com.wjw.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInfoDao extends JpaRepository<ProductInfo,String > {
    /**
     * 用户通过状态来查找可点的商品
     */
    List<ProductInfo> findByProductStatus(Integer productStatus);

}
