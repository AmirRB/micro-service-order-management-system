package com.example.inventory.service;

import com.example.inventory.entities.Inventory;
import com.example.inventory.exception.InsufficientException;
import com.example.inventory.exception.NotFoundInventoryException;
import com.example.inventory.repository.InventoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InventoryServiceImplTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private InventoryServiceImpl inventoryService;


    @BeforeEach
    public void setUp() {
        when(inventoryRepository.findByItem("A")).thenReturn(Optional.of(new Inventory(1,"A", 10)));
    }

    @Test
    void checkAvailability() {
        when(inventoryRepository.findByItem("B")).thenReturn(Optional.empty());

        Assertions.assertTrue(inventoryService.checkAvailability("A", 5).isAvailable());
        Assertions.assertTrue(inventoryService.checkAvailability("A", 10).isAvailable());
        Assertions.assertFalse(inventoryService.checkAvailability("A", 11).isAvailable());
        Assertions.assertFalse(inventoryService.checkAvailability("A", 15).isAvailable());
        Assertions.assertThrows(NotFoundInventoryException.class, () -> inventoryService.checkAvailability("B", 10));
    }

    @Test
    void reduceInventory() {

        when(inventoryRepository.save(new Inventory(1,"A",5))).thenReturn(new Inventory(1,"A",5));
        when(inventoryRepository.save(new Inventory(1,"A",0))).thenReturn(new Inventory(1,"A",0));

        Assertions.assertThrows(InsufficientException.class,() -> inventoryService.reduceInventory("A",11));

        Inventory inventory = inventoryService.reduceInventory("A", 5);
        Assertions.assertEquals(5, inventory.getQuantity());
        Assertions.assertEquals("A", inventory.getItem());

        Inventory inventory2 = inventoryService.reduceInventory("A", 5);
        Assertions.assertEquals(0, inventory2.getQuantity());
        Assertions.assertEquals("A", inventory2.getItem());


    }

}