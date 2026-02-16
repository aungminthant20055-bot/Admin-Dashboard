package com.example.AMT.Transaction.Service;

import com.example.AMT.Transaction.Dto.SaleResponse;
import org.springframework.stereotype.Service;

@Service
public interface ISaleService {
    SaleResponse getByBarcode(Long barcode);
}
