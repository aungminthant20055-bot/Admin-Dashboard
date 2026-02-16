    package com.example.AMT.Security.Dto;

    import com.example.AMT.Security.Entity.UserEntity;
    import lombok.Data;

    @Data
    public class UserResponse {
        private String userName;
        private String password;
        private String role;

        public static UserResponse mapToDto(UserEntity Entity){
            UserResponse dto = new UserResponse();
            dto.setUserName(Entity.getUserName());
            dto.setPassword(Entity.getPassword());
            dto.setRole(Entity.getRole());
            return dto;
        }
    }
