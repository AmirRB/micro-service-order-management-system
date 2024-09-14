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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private InventoryClient inventoryClient;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void createOrder() {
        String item = "tool1";
        int quantity = 5;
        when(inventoryClient.checkAvailability(item, quantity)).thenReturn(new Availability(true));
        when(inventoryClient.checkAvailability(item, 18)).thenReturn(new Availability(false));

        Order order = new Order(item, quantity, OrderStatus.NEW);
        Order orderSaved = new Order(1, item, quantity, OrderStatus.NEW);
        when(orderRepository.save(order)).thenReturn(orderSaved);
        OrderDto response = orderService.createOrder(new OrderRequest(item, quantity));

        Assertions.assertEquals(new OrderDto(1, item, quantity, OrderStatus.NEW), response);
        Assertions.assertThrows(NotFoundItemException.class, () -> orderService.createOrder(new OrderRequest(item, 18)));
    }

    @Test
    void makeShipped() {
        when(orderRepository.findById(1)).thenReturn(Optional.of(new Order(1, "tool1", 5, OrderStatus.NEW)));
        Order order = new Order(1, "tool1", 5, OrderStatus.SHIPPED);
        when(orderRepository.save(order)).thenReturn(order);

        OrderDto expected = new OrderDto(1, "tool1", 5, OrderStatus.SHIPPED);
        Assertions.assertEquals(expected, orderService.makeShipped(1));
    }

    @Test
    void getOrder() {
        Order order = new Order(1, "tool1", 5, OrderStatus.NEW);
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));
        when(orderRepository.findById(10)).thenReturn(Optional.empty());

        OrderDto response = new OrderDto(1, "tool1", 5, OrderStatus.NEW);
        Assertions.assertEquals(response, orderService.getOrder(1));
        Assertions.assertThrows(NotFoundOrderException.class, () -> orderService.getOrder(10));

    }
}