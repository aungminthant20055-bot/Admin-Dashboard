package com.example.AMT.Transaction.Controller;

import com.example.AMT.Transaction.Dto.SaleResponse;
import com.example.AMT.Transaction.Service.ISaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sales/")
public class SaleController {
    private final ISaleService saleService;
    @GetMapping("{barcode}")
    @PreAuthorize("hasRole('CASHIER')")
    public ResponseEntity<SaleResponse> getByBarcode(@Validated @PathVariable Long barcode){
        return new ResponseEntity<>(saleService.getByBarcode(barcode), HttpStatus.OK);
    }
}
