package com.ensah.microservices.productService.mapper;

import com.ensah.microservices.productService.dto.ProductDTO;
import com.ensah.microservices.productService.model.Product;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ProductMapper implements Function<Product, ProductDTO> {
    @Override
    public ProductDTO apply(Product product) {
        return new ProductDTO(
                product.getName(),
                product.getDescription(),
                product.getPrice()
        );
    }
}
