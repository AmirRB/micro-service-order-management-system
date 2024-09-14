package com.example.shipping.dto;

import com.example.shipping.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;


@Getter
@Setter
@ToString
public class Order implements Serializable {
    private Integer id;
    private String item;
    private Integer quantity;
    private OrderStatus status;

}