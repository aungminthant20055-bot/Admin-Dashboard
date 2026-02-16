package com.example.AMT.Settings.Service;

import com.example.AMT.Settings.Dto.StoreRequest;
import com.example.AMT.Settings.Dto.StoreRespone;
import com.example.AMT.Settings.Respo.Entity.StoreEntity;
import com.example.AMT.Settings.Respo.StoreRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService implements  IStoreService {
    private final StoreRepo repo;


    @Override
    public StoreRespone getSettings() {
        StoreEntity entity = repo.findById(1L)
                .orElse(new StoreEntity()); // Returns blank if DB is empty
       return mapToDto(entity);
    }


    @Override
    public StoreRespone update(StoreRequest dto) {
        // 1. Find existing settings (or create new if ID 1 doesn't exist)
        // In a POS, there is usually only ONE store row (ID 1)
        StoreEntity entity = repo.findById(1L)
                .orElse(new StoreEntity());

        // 2. Mapping DTO -> Entity (Service Layer Style)
        // REMEMBER: Using .fieldName() because it's a Record!
        entity.setId(1L); // Always keep it as the main store
        entity.setStoreName(dto.storeName());
        entity.setStoreLocation(dto.storeLocation());
        entity.setPhoneNumber(dto.phoneNumber());
        entity.setReceiptHeader(dto.receiptHeader());

        entity.setAutoPrintReceipt(dto.autoPrintReceipt());

        // 3. Save to Database
        StoreEntity saved = repo.save(entity);
        return mapToDto(saved);
    }
        // 4. Mapping Entity -> Response
        // --- The "Magic" Helper Method ---
        private StoreRespone mapToDto(StoreEntity entity){
        return new StoreRespone(
                entity.getStoreName(),
                entity.getStoreLocation()


        );
        }
    }


