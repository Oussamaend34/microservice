package com.ensah.microservices.orderService.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

public interface InventoryClient {
    Logger logger = LoggerFactory.getLogger(InventoryClient.class);
    @GetExchange("/api/inventory")
    @CircuitBreaker(name="inventory", fallbackMethod = "fallbackMethod")
    @Retry(name = "inventory")
    boolean isInStock(@RequestParam String skuCode,  @RequestParam Integer quantity);
    default boolean fallbackMethod(String code , Integer quantity, Throwable throwable) {
        logger.info("Cannot get inventory for code {}, quantity {}, error {}", code, quantity, throwable.getMessage());
        return false;
    }
}
