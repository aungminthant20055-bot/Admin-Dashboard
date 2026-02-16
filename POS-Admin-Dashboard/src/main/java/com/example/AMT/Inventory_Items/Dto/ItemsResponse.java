package com.example.AMT.Inventory_Items.Dto;

import com.example.AMT.Inventory_Items.Entity.ItemsEntity;
import lombok.Data;

@Data
public class ItemsResponse {
    private Long barcode;
    private String productName;
    private Double price;
    private Integer stock;
    private Integer minStock;
    private ItemsStatus itemStatus;


    public static  ItemsResponse mapToDto(ItemsEntity entity){
        ItemsResponse dto = new ItemsResponse();
        dto.setBarcode(entity.getBarcode());
        dto.setProductName(entity.getProductName());
        dto.setPrice(entity.getPrice());
        dto.setStock(entity.getStock());
        dto.setMinStock(entity.getMinStock());
        dto.setItemStatus(entity.getCalculatedStatus());
        return dto;
    }
}
