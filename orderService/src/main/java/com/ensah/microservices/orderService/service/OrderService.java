package com.ensah.microservices.orderService.service;

import com.ensah.microservices.orderService.client.InventoryClient;
import com.ensah.microservices.orderService.dto.OrderDTO;
import com.ensah.microservices.orderService.event.OrderPlacedEvent;
import com.ensah.microservices.orderService.mapper.OrderDTOMapper;
import com.ensah.microservices.orderService.model.Order;
import com.ensah.microservices.orderService.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final OrderDTOMapper orderDTOMapper;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public void placeOrder(OrderDTO request) {
        System.out.println("placing order");
        boolean inStock = inventoryClient.isInStock(request.skuCode(), request.quantity());
        System.out.println("In stock: " + inStock);
        if (inStock) {
            Order order = orderDTOMapper.apply(request);
            orderRepository.save(order);
            OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent();
            orderPlacedEvent.setEmail(request.email());
            orderPlacedEvent.setOrderNumber(order.getOrderNumber());
            orderPlacedEvent.setFirstName("Name");
            orderPlacedEvent.setLastName("LastName");
            CompletableFuture<SendResult<String, OrderPlacedEvent>> send = kafkaTemplate.send("order-placed", orderPlacedEvent);
            System.out.println(send.isDone());
        } else {
            throw new RuntimeException("Product not found");
        }

    }
}
