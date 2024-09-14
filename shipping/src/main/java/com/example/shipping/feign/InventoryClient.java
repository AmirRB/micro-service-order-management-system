package com.example.shipping.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "inventory", url = "${order.management.system.inventory}")
public interface InventoryClient {

    @PostMapping("/inventory/reduce")
    ResponseEntity reduceInventory(@RequestParam String item, @RequestParam int quantity);

}
