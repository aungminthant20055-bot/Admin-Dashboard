package com.example.AMT.Settings.Controller;

import com.example.AMT.Settings.Dto.StoreRequest;
import com.example.AMT.Settings.Dto.StoreRespone;
import com.example.AMT.Settings.Service.IStoreService;
import com.example.AMT.Settings.Service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/settings")
@RequiredArgsConstructor
public class StoreController {
    private final IStoreService storeService;
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','CASHIER')")
    public ResponseEntity<StoreRespone> getSettings(){
        return  new ResponseEntity<>(storeService.getSettings(), HttpStatus.OK);
    }
    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN')") // Only an Admin can change Tax/Store Info
    public ResponseEntity<StoreRespone> updateSettings(@RequestBody StoreRequest Dto){
        return new ResponseEntity<>(storeService.update(Dto),HttpStatus.OK);
    }
}
