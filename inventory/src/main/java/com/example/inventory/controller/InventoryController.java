package com.example.inventory.controller;

import com.example.inventory.dto.Availability;
import com.example.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping("/{itemId}")
    public Availability checkAvailability(@PathVariable String itemId, @RequestParam int quantity) {
        return inventoryService.checkAvailability(itemId, quantity);
    }

    @PostMapping("/reduce")
    public ResponseEntity reduceInventory(@RequestParam String item, @RequestParam int quantity) {
        inventoryService.reduceInventory(item, quantity);
        return ResponseEntity.ok().build();
    }

}
