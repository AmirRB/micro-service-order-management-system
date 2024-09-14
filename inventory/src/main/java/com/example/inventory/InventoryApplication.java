package com.example.inventory;

import com.example.inventory.service.InventoryService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class InventoryApplication {

    private final InventoryService inventoryService;

    public static void main(String[] args) {
        SpringApplication.run(InventoryApplication.class, args);
    }


    @PostConstruct
    public void init() {
        inventoryService.initialData();
    }

}
