package com.example.AMT.Transaction.Entity;

import com.example.AMT.Transaction.Dto.SaleRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleEntity {
    @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long barcode;// Connection to the product
    @Column(name = "Item")
    private String productName; // Name at the time of sale
    private Integer quantity;
    private Double unitPrice;
    private Double totalPrice;
    private LocalDateTime saleDate;


}
