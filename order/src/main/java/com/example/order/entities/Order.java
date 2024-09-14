package com.example.order.entities;


import com.example.order.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String item;
    private Integer quantity;
    private OrderStatus status;

    public Order(Integer id, String item, Integer quantity, OrderStatus status) {
        this.id = id;
        this.item = item;
        this.quantity = quantity;
        this.status = status;
    }

    public Order(String item, Integer quantity, OrderStatus status) {
        this.item = item;
        this.quantity = quantity;
        this.status = status;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return order.item.equals(this.item) &&
                order.quantity.equals(this.quantity) &&
                order.status == this.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, item, quantity, status);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", item='" + item + '\'' +
                ", quantity=" + quantity +
                ", status=" + status +
                '}';
    }
}
