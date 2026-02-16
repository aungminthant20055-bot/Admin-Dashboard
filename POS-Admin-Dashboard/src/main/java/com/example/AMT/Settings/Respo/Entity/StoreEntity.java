package com.example.AMT.Settings.Respo.Entity;

import com.example.AMT.Settings.Dto.StoreRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class StoreEntity {
    @Id

    private Long id;

    // From "Store Settings" section in your UI
    private String storeName;     // e.g., "Main Branch"
    private String storeLocation; // e.g., "123 Main Street, City"
    private String phoneNumber;   // e.g., "(555) 123-4567"

    // From "Receipt Settings" section in your UI
    private String receiptHeader; // e.g., "Thank you for shopping!"

    private Boolean autoPrintReceipt;



}