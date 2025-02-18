package com.ensah.microservices.orderService.service;

import com.ensah.microservices.orderService.client.InventoryClient;
import com.ensah.microservices.orderService.dto.OrderDTO;
import com.ensah.microservices.orderService.event.OrderPlacedEvent;
import com.ensah.microservices.orderService.mapper.OrderDTOMapper;
import com.ensah.microservices.orderService.model.Order;
import com.ensah.microservices.orderService.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final OrderDTOMapper orderDTOMapper;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public void placeOrder(OrderDTO request) {
        System.out.println();
        boolean inStock = inventoryClient.isInStock(request.skuCode(), request.quantity());
        System.out.println("In stock: " + inStock);
        if (inStock) {
            Order order = orderDTOMapper.apply(request);
            orderRepository.save(order);
            OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent(order.getOrderNumber(), request.email());
            kafkaTemplate.send("orderPlaced", orderPlacedEvent);
        } else {
            throw new RuntimeException("Product not found");
        }

    }
}
