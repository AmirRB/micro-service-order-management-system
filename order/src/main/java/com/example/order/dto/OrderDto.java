package com.example.order.dto;


import com.example.order.entities.Order;
import com.example.order.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto implements Serializable {

    private Integer id;
    private String item;
    private Integer quantity;
    private OrderStatus status;


    @Override
    public boolean equals(Object obj) {
        OrderDto orderDto = (OrderDto) obj;
        return this.item.equals(orderDto.getItem())
                && this.quantity.equals(orderDto.getQuantity())
                && this.status.equals(orderDto.getStatus());
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "id=" + id +
                ", item='" + item + '\'' +
                ", quantity=" + quantity +
                ", status=" + status +
                '}';
    }
}
