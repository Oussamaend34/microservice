package com.ensah.microservices.productService.controller;

import com.ensah.microservices.productService.dto.ProductDTO;
import com.ensah.microservices.productService.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@ResponseStatus(HttpStatus.CREATED)
@RequiredArgsConstructor
public class ProductController {
  private final ProductService productService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ProductDTO createProduct(@RequestBody ProductDTO productRequest) {
    return productService.createProduct(productRequest);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<ProductDTO> getAllProducts() {
    return productService.getAllProducts();
  }
}
