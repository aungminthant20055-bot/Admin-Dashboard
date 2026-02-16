package com.example.AMT.Inventory_Items.Service;

import com.example.AMT.Inventory_Items.Dto.InventoryStatus;
import com.example.AMT.Inventory_Items.Dto.ItemsRequest;
import com.example.AMT.Inventory_Items.Dto.ItemsResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IItemsService {
    ItemsResponse create(ItemsRequest itemsRequest);

    List<ItemsResponse> getItems();

    List<ItemsResponse> searchProducts(String query);

    ItemsResponse updateItems(Long barcode, ItemsRequest itemsRequest);

    void deleteItems(Long barcode);

    InventoryStatus getDashboardStats();
}
