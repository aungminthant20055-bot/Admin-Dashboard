package com.example.AMT.Settings.Service;

import com.example.AMT.Settings.Dto.StoreRequest;
import com.example.AMT.Settings.Dto.StoreRespone;
import org.springframework.stereotype.Service;

@Service
public interface IStoreService {
    StoreRespone getSettings();

    StoreRespone update(StoreRequest dto);
}
