package com.ensah.microservices.orderService.service;

import com.ensah.microservices.orderService.client.InventoryClient;
import com.ensah.microservices.orderService.dto.OrderDTO;
import com.ensah.microservices.orderService.mapper.OrderDTOMapper;
import com.ensah.microservices.orderService.mapper.OrderMapper;
import com.ensah.microservices.orderService.model.Order;
import com.ensah.microservices.orderService.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final OrderMapper orderMapper;
    private final OrderDTOMapper orderDTOMapper;

    public void placeOrder(OrderDTO request) {
        boolean inStock = inventoryClient.isInStock(request.skuCode(), request.quantity());
        if (inStock) {
            Order order = orderDTOMapper.apply(request);
            orderRepository.save(order);
        } else {
            throw new RuntimeException("Product not found");
        }

    }
}
