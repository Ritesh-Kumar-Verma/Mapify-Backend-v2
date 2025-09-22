package com.Mapify.Mapify.controller;

import com.Mapify.Mapify.model.UsersLocationData;
import com.Mapify.Mapify.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/search")
    public ResponseEntity<List<String>> searchUser(@RequestParam String keyword, @RequestHeader("Authorization") String authHeader ){

        String token = authHeader.substring(7);


        return userService.searchUser(keyword,token);
    }

    @PostMapping("/addselflocation")
    public ResponseEntity<String> addSelfLocation(@RequestBody UsersLocationData usersLocationData, @RequestHeader("Authorization") String authHeader){

        String token = authHeader.substring(7);
        return userService.addSelfLocation(token, usersLocationData);
    }

    @PutMapping("/sendfriendrequest")
    public ResponseEntity<String> sendFriendRequest(@RequestParam String username , @RequestHeader("Authorization") String authHeader  ){

        String token = authHeader.substring(7);

        return userService.sendFriendRequest(token,username);



    }






}
