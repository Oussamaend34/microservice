package com.ensah.microservices.productService.dto;

import java.math.BigDecimal;

public record ProductDTO(String name, String description, BigDecimal price) {}
