package com.example.AMT.Security.Service;

import com.example.AMT.Security.Dto.UserRequest;
import com.example.AMT.Security.Dto.UserResponse;
import com.example.AMT.Security.Entity.UserEntity;
import com.example.AMT.Security.Repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements  IUserService {
    private final UserRepo repo;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;
    private final JWTService jwtService;

    @Override
    public UserResponse register(UserRequest userRequest) {

        UserEntity user = UserEntity.mapToEntiy(userRequest);

        user.setPassword(encoder.encode(user.getPassword()));

        user.setRole(userRequest.getRole());

        UserEntity savedUser = repo.save(user); // 🔥 SAVE TO DB

        return UserResponse.mapToDto(savedUser);
    }


    @Override
    public String verify(UserRequest userRequest) {
        Authentication auth =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUserName(),userRequest.getPassword()));
        if(auth.isAuthenticated()){
            return jwtService.generateToken(userRequest.getUserName(),auth.getAuthorities());

        }
        return "Fail";
    }
}
