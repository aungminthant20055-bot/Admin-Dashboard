package com.example.AMT.Inventory_Items.ItemRepo;

import com.example.AMT.Inventory_Items.Dto.InventoryStatus;
import com.example.AMT.Inventory_Items.Entity.ItemsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ItemRepo extends JpaRepository<ItemsEntity, Long> {

    Optional<ItemsEntity> findByBarcode(Long barcode);

    List<ItemsEntity> findByProductNameStartingWithIgnoreCase(String productName);

    @Query("""
    SELECT new com.example.AMT.Inventory_Items.Dto.InventoryStatus(
        SUM(i.price * i.stock),
        COUNT(CASE WHEN i.stock > 0 AND i.stock <= i.minStock THEN 1 END),
        COUNT(CASE WHEN i.stock <= 0 THEN 1 END),
        COUNT(i) 
    )
    FROM ItemsEntity i
""")
    InventoryStatus getDashboardStats();
}
