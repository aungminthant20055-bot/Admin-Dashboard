package com.example.AMT.Inventory_Items.Service;

import com.example.AMT.Inventory_Items.Dto.InventoryStatus;
import com.example.AMT.Inventory_Items.Dto.ItemsRequest;
import com.example.AMT.Inventory_Items.Dto.ItemsResponse;
import com.example.AMT.Inventory_Items.Entity.ItemsEntity;
import com.example.AMT.Inventory_Items.ItemRepo.ItemRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ItemsService implements IItemsService {
    private final ItemRepo repo;
    @Override
    @Transactional
    public ItemsResponse create(ItemsRequest itemsRequest) {
        ItemsEntity items = ItemsEntity.mapToEntity(itemsRequest);
        items.setItemsStatus(items.getCalculatedStatus());
         repo.save(items);
         return ItemsResponse.mapToDto(items);
    }

    @Override
    public List<ItemsResponse> getItems() {
        List<ItemsEntity> items = repo.findAll();
        return items.stream().map(ItemsResponse::mapToDto).toList();
    }

    @Override
    public List<ItemsResponse> searchProducts(String query) {
        //check it's number or not
        if(query!= null && query.matches("\\d+")){
            Long barcode = Long.parseLong(query);
            // find by Barcode  n return
           return  repo.findByBarcode(barcode)
                   .map(itemsEntity -> List.of(ItemsResponse.mapToDto(itemsEntity)))
                   .orElse(List.of());
        }
        //find by productName n return
        List<ItemsEntity> items = repo.findByProductNameStartingWithIgnoreCase(query);
        return items.stream().map(ItemsResponse::mapToDto).toList();
    }

    @Override
    @Transactional
    public ItemsResponse updateItems(Long barcode, ItemsRequest itemsRequest) {
        ItemsEntity items = repo.findById(barcode).orElseThrow(()-> new RuntimeException("barcode not found"));

        items.setProductName(itemsRequest.getProductName());
        items.setPrice(itemsRequest.getPrice());
        items.setStock(itemsRequest.getStock());
        items.setMinStock(itemsRequest.getMinStock());
        // 3. IMPORTANT: Recalculate status based on new stock/minStock
        items.setItemsStatus(items.getCalculatedStatus());
        repo.save(items);
        return ItemsResponse.mapToDto(items);
    }

    @Override
    public void deleteItems(Long barcode) {
        if (!repo.existsById(barcode)) {
            throw new RuntimeException("Cannot delete: Barcode " + barcode + " does not exist");
        }
        repo.deleteById(barcode);
    }
    @Override
    @Transactional(readOnly = true) // Use readOnly for better performance in Java 25
    public InventoryStatus getDashboardStats() {
        return repo.getDashboardStats();
    }
}



