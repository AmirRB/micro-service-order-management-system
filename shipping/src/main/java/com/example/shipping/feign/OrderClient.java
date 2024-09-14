package com.example.shipping.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "order", url = "${order.management.system.order}")
public interface OrderClient {

    @PutMapping("orders/shipped")
    ResponseEntity makeShipped(@RequestParam Integer orderId);

}
