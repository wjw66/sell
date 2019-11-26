package com.wjw.service;

import com.wjw.dataobject.ProductInfo;
import com.wjw.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductInfo findOne(String productId);
    //查询所有已上架的商品
    List<ProductInfo> findUpAll();
    //分页查询所有商品(包括未上架)
    Page<ProductInfo> findAll(Pageable pageable);
    //保存商品
    ProductInfo save(ProductInfo productInfo);
    //加库存
    void increaseStock(List<CartDTO> cartDTO);
    //减库存(CartDTO字段为商品id和数量,通过id查询相应商品,为商品加减定义好的数量)
    void decreaseStock(List<CartDTO> cartDTOList);
}
