package com.example.inventory.service;

import com.example.inventory.dto.Availability;
import com.example.inventory.entities.Inventory;

public interface InventoryService {

    void initialData();

    Availability checkAvailability(String item, int quantity);

    Inventory reduceInventory(String item, int quantity);
}
