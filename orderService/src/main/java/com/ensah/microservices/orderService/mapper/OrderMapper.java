package com.ensah.microservices.orderService.mapper;

import com.ensah.microservices.orderService.dto.OrderDTO;
import com.ensah.microservices.orderService.model.Order;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class OrderMapper implements Function<Order, OrderDTO> {
    @Override
    public OrderDTO apply(Order order) {
        return new OrderDTO(
                order.getId(),
                order.getOrderNumber(),
                order.getSkuCode(),
                order.getPrice(),
                order.getQuantity()
        );
    }
}
