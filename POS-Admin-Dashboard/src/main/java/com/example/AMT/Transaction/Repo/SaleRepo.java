package com.example.AMT.Transaction.Repo;

import com.example.AMT.Transaction.Entity.SaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SaleRepo extends JpaRepository<SaleEntity, Long> {
    Optional<SaleEntity> findByBarcode(Long barcode);
}
