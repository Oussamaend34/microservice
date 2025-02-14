package com.ensah.microservices.productService.service;

import com.ensah.microservices.productService.mapper.ProductMapper;
import com.ensah.microservices.productService.model.Product;
import com.ensah.microservices.productService.dto.ProductDTO;
import com.ensah.microservices.productService.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
  private final ProductRepository productRepository;
  private final ProductMapper productMapper;

  public ProductDTO createProduct(ProductDTO request) {
    Product product =
        Product.builder()
            .name(request.name())
            .description(request.description())
            .price(request.price())
            .build();
    productRepository.save(product);
    log.info("Product {} created", product);

    return productMapper.apply(product);
  }
  public List<ProductDTO> getAllProducts() {
    return productRepository.findAll()
            .stream()
            .map(productMapper)
            .collect(Collectors.toList());
  }
}
