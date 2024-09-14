package com.example.order.service;

import com.example.order.dto.OrderDto;
import com.example.order.dto.OrderRequest;
import com.example.order.enums.OrderStatus;

import java.util.List;

public interface OrderService {
    OrderDto createOrder(OrderRequest input);

    OrderDto makeShipped(Integer orderId);

    List<OrderDto> getOrders();

    OrderDto getOrder(Integer orderId);
}
