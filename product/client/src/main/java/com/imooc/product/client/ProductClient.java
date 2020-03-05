package com.imooc.product.client;

import com.imooc.product.client.ProductClient.ProductClientFallback;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.imooc.product.common.*;

/**
 * Created by 廖师兄 2017-12-10 21:04
 */
@FeignClient(name = "product", fallback = ProductClientFallback.class)
public interface ProductClient {

  @PostMapping("/product/listForOrder")
  List<ProductInfoOutput> listForOrder(@RequestBody List<String> productIdList);

  @PostMapping("/product/decreaseStock")
  void decreaseStock(@RequestBody List<DecreaseStockInput> decreaseStockInputList);

  @Component
  class ProductClientFallback implements ProductClient{

    @Override
    public List<ProductInfoOutput> listForOrder(List<String> productIdList) {
      return null;
    }

    @Override
    public void decreaseStock(List<DecreaseStockInput> decreaseStockInputList) {

    }
  }
}
