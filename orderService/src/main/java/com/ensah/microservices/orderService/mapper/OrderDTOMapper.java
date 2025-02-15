package com.ensah.microservices.orderService.mapper;

import com.ensah.microservices.orderService.dto.OrderDTO;
import com.ensah.microservices.orderService.model.Order;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class OrderDTOMapper implements Function<OrderDTO, Order> {
    @Override
    public Order apply(OrderDTO orderDTO) {
        return new Order(
                orderDTO.id(),
                orderDTO.orderNumber(),
                orderDTO.skuCode(),
                orderDTO.price(),
                orderDTO.quantity()
        );
    }
}
