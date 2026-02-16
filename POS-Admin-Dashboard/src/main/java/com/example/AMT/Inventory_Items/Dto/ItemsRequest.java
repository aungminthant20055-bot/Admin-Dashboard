package com.example.AMT.Inventory_Items.Dto;

import lombok.Data;

@Data
public class ItemsRequest {
    private Long barcode;
    private String productName;
    private Double price;
    private Integer stock;
    private Integer minStock;
    private ItemsStatus itemstatus;




}
