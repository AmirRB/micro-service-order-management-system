package com.example.inventory.service;

import com.example.inventory.dto.Availability;
import com.example.inventory.entities.Inventory;
import com.example.inventory.exception.InsufficientException;
import com.example.inventory.exception.NotFoundInventoryException;
import com.example.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService {

    private static final Logger log = LoggerFactory.getLogger(InventoryServiceImpl.class);
    private final InventoryRepository inventoryRepository;

    @Override
    public void initialData() {
        for (int i = 1; i <= 10; i++) {
            Inventory inventory = new Inventory("tool" + i, i + 10);
            inventoryRepository.save(inventory);
            log.info("Inventory saved: " + inventory);
        }
    }

    private Inventory getInventory(String item) {
        return inventoryRepository.findByItem(item).orElseThrow(NotFoundInventoryException::new);
    }

    @Override
    public Availability checkAvailability(String item, int quantity) {
        Inventory inventory = getInventory(item);
        boolean isAvailable = inventory.getQuantity() >= quantity;
        return new Availability(isAvailable);
    }

    @Override
    public Inventory reduceInventory(String item, int quantity) {
        Inventory inventory = getInventory(item);
        if (inventory.getQuantity() < quantity) {
            throw new InsufficientException();
        }
        inventory.setQuantity(inventory.getQuantity() - quantity);
        Inventory save = inventoryRepository.save(inventory);
        log.info("Inventory reduced to " + inventory);
        return save;
    }

}
