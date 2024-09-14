package com.example.order.service;

import com.example.order.dto.Availability;
import com.example.order.dto.OrderDto;
import com.example.order.dto.OrderRequest;
import com.example.order.entities.Order;
import com.example.order.enums.OrderStatus;
import com.example.order.exception.NotFoundItemException;
import com.example.order.exception.NotFoundOrderException;
import com.example.order.feign.InventoryClient;
import com.example.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final InventoryClient inventoryClient;
    private final OrderRepository orderRepository;
    private final RabbitTemplate rabbitTemplate;

    @Override
    public OrderDto createOrder(OrderRequest input) {
        log.info("Create " + input.getItem() + " order");
        Availability availability = inventoryClient.checkAvailability(input.getItem(), input.getQuantity());
        log.info("Check availability " + availability.isAvailable());
        if (!availability.isAvailable()) {
            throw new NotFoundItemException();
        }

        Order order = new Order(input.getItem(), input.getQuantity(), OrderStatus.NEW);
        Order savedOrder = orderRepository.save(order);
        log.info("Saved order " + savedOrder);

        rabbitTemplate.convertAndSend("order.queue", savedOrder);
        log.info("Sending order to queue");
        return new OrderDto(savedOrder.getId(), savedOrder.getItem(), savedOrder.getQuantity(), savedOrder.getStatus());
    }

    @Override
    public OrderDto makeShipped(Integer orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(NotFoundOrderException::new);
        order.setStatus(OrderStatus.SHIPPED);
        Order savedOrder = orderRepository.save(order);
        log.info("order make shipped " + savedOrder);
        return new OrderDto(savedOrder.getId(), savedOrder.getItem(), savedOrder.getQuantity(), savedOrder.getStatus());
    }


    @Override
    public List<OrderDto> getOrders() {
        return orderRepository.findAll().stream().map(order -> new OrderDto(order.getId(), order.getItem(), order.getQuantity(), order.getStatus())).toList();
    }

    @Override
    public OrderDto getOrder(Integer orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(NotFoundOrderException::new);
        return new OrderDto(order.getId(), order.getItem(), order.getQuantity(), order.getStatus());
    }

}
