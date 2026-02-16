package com.example.AMT.Transaction.Dto;

import com.example.AMT.Transaction.Entity.SaleEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SaleResponse {
    private Long barcode;
    private String productName; // Name at the time of sale
    private Integer quantity;
    private Double unitPrice;
    private Double totalPrice;
    private LocalDateTime saleDate;

    public static SaleResponse mapToDto(SaleEntity entity){
        SaleResponse  Dto = new  SaleResponse();
        Dto.setBarcode(entity.getBarcode());
        Dto.setProductName(entity.getProductName());
        Dto.setQuantity(entity.getQuantity());
        Dto.setUnitPrice(entity.getUnitPrice());
        Dto.setTotalPrice(entity.getTotalPrice());
        Dto.setSaleDate(entity.getSaleDate());
        return Dto;
    }

}
