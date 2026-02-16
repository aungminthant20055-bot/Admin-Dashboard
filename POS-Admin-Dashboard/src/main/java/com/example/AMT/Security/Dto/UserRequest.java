package com.example.AMT.Security.Dto;

import lombok.Data;

@Data
public class UserRequest {
    private String userName;
    private String password;
    private String role;
}
