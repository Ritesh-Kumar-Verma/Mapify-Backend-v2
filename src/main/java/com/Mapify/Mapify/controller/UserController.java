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

    @PostMapping("/sendfriendrequest")
    public ResponseEntity<String> sendFriendRequest(@RequestParam String username , @RequestHeader("Authorization") String authHeader  ){
        String token = authHeader.substring(7);
        return userService.sendFriendRequest(token,username);
    }

    @PutMapping("/acceptfriendrequest")
    public ResponseEntity<String> acceptFriendRequest(@RequestHeader("Authorization") String authHeader , @RequestParam String username ){
        String token = authHeader.substring(7);
        return userService.acceptFriendRequest(token , username);

    }


    @GetMapping("/getpendingrequests")
    public ResponseEntity<List<String>> getPendingRequest( @RequestHeader("Authorization") String authHeader ){
        String token = authHeader.substring(7);
        return userService.getPendingRequest(token);
    }

    @GetMapping("/getmyrequests")
    public ResponseEntity<List<String>> getMyRequest(@RequestHeader("Authorization") String authheader){
        String token = authheader.substring(7);
        return userService.getMyRequest(token);
    }

    @DeleteMapping("/rejectrequest")
    public ResponseEntity<String> rejectRequest(@RequestHeader("Authorization") String authHeader , @RequestParam String username){
        String token = authHeader.substring(7);
        return userService.rejectRequest(token , username);

    }

    @DeleteMapping("/cancelrequest")
    public ResponseEntity<String> cancelRequest(@RequestHeader("Authorization") String authHeader , @RequestParam String username){
        String token = authHeader.substring(7);
        return userService.cancelRequest(token , username);
    }


    @GetMapping("/getfriendslist")
    public ResponseEntity<List<String>> getFriendsList(@RequestHeader("Authorization") String authHeader){
        String token = authHeader.substring(7);
        return userService.getFriendsList(token);
    }

    @GetMapping("/getlocation")
    public ResponseEntity<UsersLocationData> getlocation(@RequestHeader("Authorization") String authHeader , @RequestParam String username){
        String token = authHeader.substring(7);
        return userService.getLocation(token,username);
    }

    @DeleteMapping("/deletefriendship")
    public ResponseEntity<String> deleteFriendship(@RequestHeader("Authorization") String authHeader , @RequestParam String username){
        String token = authHeader.substring(7);
        return userService.deleteFriendship(token, username);
    }




}
