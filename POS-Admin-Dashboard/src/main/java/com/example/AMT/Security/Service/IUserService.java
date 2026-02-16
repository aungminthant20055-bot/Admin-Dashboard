package com.example.AMT.Security.Service;

import com.example.AMT.Security.Dto.UserRequest;
import com.example.AMT.Security.Dto.UserResponse;
import org.springframework.stereotype.Service;

public interface IUserService {
    UserResponse register(UserRequest userRequest);

    String verify(UserRequest userRequest);
}
