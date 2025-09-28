package com.Mapify.Mapify.controller;

import com.Mapify.Mapify.model.Users;
import com.Mapify.Mapify.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    LoginService loginService;

    @GetMapping("/health")
    public String health(){
        return "OK";
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Users user){
        if(user.getUsername() !=null && user.getPassword() !=null && user.getEmail()!=null ){
            return loginService.registerUser(user);
        }
        return new ResponseEntity<>("Incomplete Data" , HttpStatus.NOT_ACCEPTABLE);
    }

    @PostMapping("/login")
    public ResponseEntity<String> verifyUser(@RequestBody Users user){
        return loginService.verifyUser(user);
    }

    @GetMapping("/getdata")
    public ResponseEntity<String> getData(){
        System.out.println("asdhaslkdhas");
        return new ResponseEntity<>("Worked",HttpStatus.OK);
    }

    @PostMapping("/changepassword")
    public ResponseEntity<String> changePassword(@RequestHeader("Authorization") String autheHeader , @RequestBody Map<String , String> body ){
        String token = autheHeader.substring(7);
        return loginService.changePassword(token,body);
    }




}
