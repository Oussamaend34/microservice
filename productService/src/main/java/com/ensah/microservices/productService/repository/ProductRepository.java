package com.ensah.microservices.productService.repository;

import com.ensah.microservices.productService.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {}
