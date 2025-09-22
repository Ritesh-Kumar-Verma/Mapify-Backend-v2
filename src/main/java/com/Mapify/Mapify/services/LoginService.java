package com.Mapify.Mapify.services;

import com.Mapify.Mapify.model.Users;
import com.Mapify.Mapify.repository.UserLoginRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    UserLoginRepo userLoginRepo;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    JWTService jwtService;

    BCryptPasswordEncoder encode=new BCryptPasswordEncoder(10);


    public String verifyUser(Users user) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername() , user.getPassword()));
        if(authentication.isAuthenticated()){
            return jwtService.generateToken(user.getUsername());
        }
        return "Wrong Credentials";
    }


    public ResponseEntity<?> registerUser(Users user) {

        Optional<Users> u1 = userLoginRepo.findByUsername(user.getUsername());

        if(u1.isPresent()){
            return new ResponseEntity<>("Username Already Exist" , HttpStatus.NOT_ACCEPTABLE);
        }
        user.setPassword(encode.encode(user.getPassword()));
        user.setRole("USER");
        userLoginRepo.save(user);
        return new ResponseEntity<>(user.getUsername() ,HttpStatus.OK);

    }
}
