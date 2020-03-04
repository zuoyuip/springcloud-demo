package com.imooc.product.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.product.common.DecreaseStockInput;
import com.imooc.product.common.ProductInfoOutput;
import com.imooc.product.dataobject.ProductInfo;
import com.imooc.product.enums.ProductStatusEnum;
import com.imooc.product.enums.ResultEnum;
import com.imooc.product.exception.ProductException;
import com.imooc.product.repository.ProductInfoRepository;
import com.imooc.product.service.ProductService;
import com.rabbitmq.tools.json.JSONUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by 廖师兄 2017-12-09 21:59
 */
@Service
public class ProductServiceImpl implements ProductService {

  private final ProductInfoRepository productInfoRepository;
  private final AmqpTemplate amqpTemplate;

  public ProductServiceImpl(ProductInfoRepository productInfoRepository,
      AmqpTemplate amqpTemplate) {
    this.productInfoRepository = productInfoRepository;
    this.amqpTemplate = amqpTemplate;
  }

  @Override
  public List<ProductInfo> findUpAll() {
    return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
  }

  @Override
  public List<ProductInfoOutput> findList(List<String> productIdList) {
    return productInfoRepository.findByProductIdIn(productIdList).stream()
        .map(e -> {
          ProductInfoOutput output = new ProductInfoOutput();
          BeanUtils.copyProperties(e, output);
          return output;
        })
        .collect(Collectors.toList());
  }

  @SneakyThrows
  @Override
  public void decreaseStock(List<DecreaseStockInput> decreaseStockInputList) {
    List<ProductInfoOutput> productInfoOutputs = decreaseStockProcess(decreaseStockInputList).stream()
        .map(productInfo -> {
          ProductInfoOutput productInfoOutput = new ProductInfoOutput();
          BeanUtils.copyProperties(productInfo, productInfoOutput);
          return productInfoOutput;
        }).collect(Collectors.toList());
    amqpTemplate.convertAndSend("productInfo", new ObjectMapper().writeValueAsString(productInfoOutputs));
  }

  @Transactional(rollbackFor = {ProductException.class, Exception.class})
  List<ProductInfo> decreaseStockProcess(List<DecreaseStockInput> decreaseStockInputList) {
    List<ProductInfo> productInfos = new ArrayList<>();
    for (DecreaseStockInput decreaseStockInput : decreaseStockInputList) {
      Optional<ProductInfo> productInfoOptional = productInfoRepository
          .findById(decreaseStockInput.getProductId());
      //判断商品是否存在
      if (!productInfoOptional.isPresent()) {
        throw new ProductException(ResultEnum.PRODUCT_NOT_EXIST);
      }

      ProductInfo productInfo = productInfoOptional.get();
      //库存是否足够
      int result = productInfo.getProductStock() - decreaseStockInput.getProductQuantity();
      if (result < 0) {
        throw new ProductException(ResultEnum.PRODUCT_STOCK_ERROR);
      }
      productInfo.setProductStock(result);
      productInfoRepository.save(productInfo);
      productInfos.add(productInfo);
    }
    return productInfos;
  }
}
