package com.example.AMT.Security.Entity;

import com.example.AMT.Security.Dto.UserRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String password;
    private String role;

    public static UserEntity mapToEntiy(UserRequest Dto) {
        UserEntity Entity = new UserEntity();
        Entity.setUserName(Dto.getUserName());
        Entity.setPassword(Dto.getPassword());
        Entity.setRole(Dto.getRole());
        return Entity;
    }
}
