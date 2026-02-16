package com.example.AMT.Inventory_Items.Entity;

import com.example.AMT.Inventory_Items.Dto.ItemsRequest;
import com.example.AMT.Inventory_Items.Dto.ItemsStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemsEntity {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "barcode")
    private Long barcode;

    @Column(name = "product_Name", nullable = false)
    private String productName;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "min_Stock", nullable = false)
    private Integer minStock;



    @Enumerated(EnumType.STRING)
    @Column(name = "item_Status")
    private ItemsStatus itemsStatus;



    public static  ItemsEntity mapToEntity(ItemsRequest Dto){
        ItemsEntity entity = new ItemsEntity();
        entity.setBarcode(Dto.getBarcode());
        entity.setProductName(Dto.getProductName());
        entity.setPrice(Dto.getPrice());
        entity.setStock(Dto.getStock());
        entity.setMinStock(Dto.getMinStock());
        //If the database doesn't know the status, you cannot search for it.
        // You can't write a SQL query like: SELECT * FROM items WHERE item_status = 'OUT_OF_STOCK'
        entity.setItemsStatus(Dto.getItemstatus());
       return  entity;
    }
    public ItemsStatus getCalculatedStatus() {
        if (this.stock <= 0) return ItemsStatus.OutOfStock;
        if (this.stock <= this.minStock) return ItemsStatus.Low_Stock;
        return ItemsStatus.In_Stock;
    }



}
