package com.example.AMT.Security.Service;

import com.example.AMT.Security.Entity.UserEntity;
import com.example.AMT.Security.Repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepo repo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = repo.findByUserName(username);
        if(user== null){
            throw new UsernameNotFoundException("username not found");
        }
        return new  UserPrincipal(user);
    }
}
