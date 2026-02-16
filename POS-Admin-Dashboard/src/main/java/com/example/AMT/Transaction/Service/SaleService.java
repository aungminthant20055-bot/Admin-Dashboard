package com.example.AMT.Transaction.Service;

import com.example.AMT.Inventory_Items.Entity.ItemsEntity;
import com.example.AMT.Inventory_Items.ItemRepo.ItemRepo;
import com.example.AMT.Transaction.Dto.SaleResponse;
import com.example.AMT.Transaction.Entity.SaleEntity;
import com.example.AMT.Transaction.Repo.SaleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SaleService implements ISaleService{
    private final ItemRepo itemRepo;
    private final SaleRepo saleRepo;


//    @Override
//    public SaleResponse getByBarcode(Long barcode) {
//        // 1. Look into your 'ItemsEntity' table
//        ItemsEntity item = itemRepo.findByBarcode(barcode).orElseThrow(()->new RuntimeException("Item not found"));
//
//        // 2. Create the Sale record using data FROM that item
//        SaleEntity sale = new  SaleEntity();
//        sale.setBarcode(item.getBarcode());
//        sale.setProductName(item.getProductName());
//        sale.setUnitPrice(item.getPrice());
////        sale.setQuantity(1);
////        sale.setTotalPrice(item.getPrice()*1);
//        Double price = item.getPrice();
//        Integer qty = 1;
//
//        sale.setUnitPrice(price);
//        sale.setQuantity(qty);
//        sale.setTotalPrice(price * qty); // This is clean and safe
//        sale.setSaleDate(LocalDateTime.now());
//
//        // 3. Save to your SaleRepo
//        saleRepo.save(sale);
//
//        return SaleResponse.mapToDto(sale);
//    }

@Override
public SaleResponse getByBarcode(Long barcode) {
    // 1. Always find the item in inventory first to get the latest price/name
    ItemsEntity item = itemRepo.findByBarcode(barcode)
            .orElseThrow(() -> new RuntimeException("Item not found"));

    // 2. CHECK HISTORY: Does this barcode already exist in our Sales table?
    Optional<SaleEntity> existingSale = saleRepo.findByBarcode(barcode);

    //Optional is like a safety box.
    //Instead of getting a result that might be "nothing," you get a box.
    //You check if the box is empty (isPresent()) before you try to touch the data inside.

    SaleEntity sale;//Empty Bucket

//    Why write it like this?
//    If you created the variable inside the if block,
//    you couldn't use it outside the if block.
//    By declaring it at the top, you can fill it in two different ways but use it once at the end.

    if (existingSale.isPresent()) {
        // IF YES: Get the old record and update it
        sale = existingSale.get();
        sale.setQuantity(sale.getQuantity() + 1); // Add 1 to current qty
        sale.setTotalPrice(sale.getUnitPrice() * sale.getQuantity()); // Recalculate
    } else {
        // IF NO: Create a brand new record
        sale = new SaleEntity();
        sale.setBarcode(item.getBarcode());
        sale.setProductName(item.getProductName());
        sale.setUnitPrice(item.getPrice());
        sale.setQuantity(1);
        sale.setTotalPrice(item.getPrice() * 1);
        sale.setSaleDate(LocalDateTime.now());
    }

    // 3. Save (JPA will "Update" if it exists, or "Insert" if it's new)
    saleRepo.save(sale);

    return SaleResponse.mapToDto(sale);
}
}
