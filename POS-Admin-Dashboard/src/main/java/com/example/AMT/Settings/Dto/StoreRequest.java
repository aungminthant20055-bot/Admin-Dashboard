package com.example.AMT.Settings.Dto;

import lombok.Data;


public record StoreRequest(
        String storeName,
        String storeLocation,
        String phoneNumber,
        String receiptHeader,

        Boolean autoPrintReceipt

) {
}
