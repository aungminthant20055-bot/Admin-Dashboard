package com.example.AMT.Inventory_Items.Dto;

public record InventoryStatus(
        Double totalValue,
        Long lowStockCount,
        Long outOfStockCount,
        Long totalItems
) {
}
