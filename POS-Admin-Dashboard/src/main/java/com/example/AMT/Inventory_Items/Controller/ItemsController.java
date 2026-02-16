package com.example.AMT.Inventory_Items.Controller;

import com.example.AMT.Inventory_Items.Dto.InventoryStatus;
import com.example.AMT.Inventory_Items.Dto.ItemsRequest;
import com.example.AMT.Inventory_Items.Dto.ItemsResponse;
import com.example.AMT.Inventory_Items.Service.IItemsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/inventory")
@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
@RequiredArgsConstructor
public class ItemsController {

    private final IItemsService itemsService;

    @PostMapping("/create")
    public ResponseEntity<ItemsResponse> createItems(@Validated @RequestBody ItemsRequest itemsRequest) {
        return new ResponseEntity<>(itemsService.create(itemsRequest), HttpStatus.CREATED);
    }

    @GetMapping("/getAllItems")
    public ResponseEntity<List<ItemsResponse>> getItems() {
        return new ResponseEntity<>(itemsService.getItems(),HttpStatus.OK);
    }
    @GetMapping("/search")
    public ResponseEntity<List<ItemsResponse>> searchProducts(@Validated @RequestParam String query){
        return new ResponseEntity<>(itemsService.searchProducts(query),HttpStatus.OK);
    }
    @PutMapping("/update/{barcode}")
    public ResponseEntity<ItemsResponse> updateItems(@Validated @PathVariable Long barcode, @Validated @RequestBody ItemsRequest itemsRequest) {
        return  new ResponseEntity<>(itemsService.updateItems(barcode,itemsRequest),HttpStatus.OK);
    }
    @DeleteMapping("/delete/{barcode}")
    public ResponseEntity<String> deleteItems(@Validated @PathVariable Long barcode){
        itemsService.deleteItems(barcode);
        return new ResponseEntity<>("Deleted Successfully",HttpStatus.OK);
    }
    @GetMapping("/stats")
    public ResponseEntity<InventoryStatus> getInventoryStats() {
        InventoryStatus stats = itemsService.getDashboardStats();
        return ResponseEntity.ok(stats);
    }

}
