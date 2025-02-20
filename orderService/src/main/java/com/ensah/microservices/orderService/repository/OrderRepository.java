package com.ensah.microservices.orderService.repository;

import com.ensah.microservices.orderService.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
