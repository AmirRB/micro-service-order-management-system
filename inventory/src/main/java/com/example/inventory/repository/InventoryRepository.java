package com.example.inventory.repository;
import java.util.Optional;
import com.example.inventory.entities.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
    Optional<Inventory> findByItem(String item);
}
