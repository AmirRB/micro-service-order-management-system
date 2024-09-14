package com.example.inventory.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String item;
    private Integer quantity;


    public Inventory(String item, Integer quantity) {
        this.item = item;
        this.quantity = quantity;
    }


    @Override
    public boolean equals(Object obj) {
        Inventory inventory = (Inventory) obj;
        return inventory.getItem().equals(this.item) && inventory.getQuantity().equals(this.quantity);
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "id=" + id +
                ", item='" + item + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}

