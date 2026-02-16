package com.example.AMT.Security.Repo;

import com.example.AMT.Security.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserEntity, Integer> {

    UserEntity findByUserName(String userName);


}
