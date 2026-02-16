package com.example.AMT.Security.Controller;

import com.example.AMT.Security.Dto.UserRequest;
import com.example.AMT.Security.Dto.UserResponse;
import com.example.AMT.Security.Service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRequest userRequest) {
        return new ResponseEntity<>(userService.register(userRequest), HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserRequest userRequest) {
        // Usually, login returns a String (the JWT Token), not a UserResponse
        return  new ResponseEntity<>(userService.verify(userRequest),HttpStatus.CREATED);
    }
}
