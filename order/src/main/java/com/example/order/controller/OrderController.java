package com.example.order.controller;

import com.example.order.dto.OrderDto;
import com.example.order.dto.OrderRequest;

import com.example.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public List<OrderDto> getAllOrders() {
        return orderService.getOrders();
    }

    @GetMapping("{orderId}")
    public OrderDto getAllOrders(@PathVariable Integer orderId) {
        return orderService.getOrder(orderId);
    }

    @PostMapping
    public OrderDto createOrder(@RequestBody OrderRequest order) {
        return orderService.createOrder(order);
    }

    @PutMapping("shipped")
    public OrderDto makeShipped(@RequestParam Integer orderId) {
        return orderService.makeShipped(orderId);
    }

}
