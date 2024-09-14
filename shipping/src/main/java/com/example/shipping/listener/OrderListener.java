package com.example.shipping.listener;

import com.example.shipping.dto.Order;
import com.example.shipping.feign.InventoryClient;
import com.example.shipping.feign.OrderClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderListener {

    private final InventoryClient inventoryClient;
    private final OrderClient orderClient;

    @RabbitListener(queues = "order.queue")
    public void receive(Order in) {
        try {
            log.info("Order received: {}", in);
            inventoryClient.reduceInventory(in.getItem(), in.getQuantity());
            log.info("Order reduced");
            proccess();
            orderClient.makeShipped(in.getId());
            log.info("Order make shipped");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void proccess() {
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
