package com.Mapify.Mapify.services;

import com.Mapify.Mapify.model.Users;
import com.Mapify.Mapify.model.UsersLocationData;
import com.Mapify.Mapify.repository.UserLocationRepo;
import com.Mapify.Mapify.repository.UserLoginRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    UserLoginRepo userLoginRepo;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    JWTService jwtService;

    @Autowired
    UserLocationRepo userLocationRepo;



    BCryptPasswordEncoder encode=new BCryptPasswordEncoder(10);


    public ResponseEntity<String> verifyUser(Users user) {
        try{
            Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername() , user.getPassword()));
            return new ResponseEntity<>(jwtService.generateToken(user.getUsername()), HttpStatus.OK );

        }
        catch (BadCredentialsException e){
            return new ResponseEntity<>("Wrong Credentials" , HttpStatus.UNAUTHORIZED);
        }
    }


    public ResponseEntity<String> registerUser(Users user) {
        if( user.getUsername().trim().length() == 0 || user.getPassword().trim().length() == 0 || user.getEmail().trim().length() == 0   ){
            return new ResponseEntity<>("Blank Data Not Acceptable" , HttpStatus.NOT_ACCEPTABLE);
        }

        Optional<Users> u1 = userLoginRepo.findByUsername(user.getUsername());

        if(u1.isPresent()){
            return new ResponseEntity<>("Username Already Exist" , HttpStatus.NOT_ACCEPTABLE);
        }
        user.setPassword(encode.encode(user.getPassword()));
        user.setRole("USER");
        userLoginRepo.save(user);

        UsersLocationData usersLocationData = new UsersLocationData(user , 0.0,0.0);

        userLocationRepo.save(usersLocationData);




        return new ResponseEntity<>(jwtService.generateToken(user.getUsername()) ,HttpStatus.OK);

    }


    public ResponseEntity<String> changePassword(String token, Map<String, String> body) {
        String username = jwtService.extractUsername(token);

        Users user = userLoginRepo.findByUsername(username)
                .orElseThrow(()->new RuntimeException("User Not Found"));

        String currentPassword = body.get("currentPassword");
        String newPassword = body.get("newPassword");

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

        if(encoder.matches(currentPassword, user.getPassword())){
            user.setPassword(encoder.encode(newPassword));
            userLoginRepo.save(user);
            return new ResponseEntity<>("Success" , HttpStatus.OK);
        }
        return new ResponseEntity<>("Wrong Password", HttpStatus.NOT_ACCEPTABLE);
    }



}
