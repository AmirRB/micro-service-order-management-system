package com.example.order.feign;

import com.example.order.dto.Availability;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "inventory", url = "${order.management.system.inventory}")
public interface InventoryClient {

    @GetMapping("/inventory/{itemId}")
    Availability checkAvailability(@PathVariable String itemId, @RequestParam int quantity);

}
