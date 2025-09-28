package com.Mapify.Mapify.services;

import com.Mapify.Mapify.model.Friendship;
import com.Mapify.Mapify.model.Users;
import com.Mapify.Mapify.model.UsersLocationData;
import com.Mapify.Mapify.repository.FriendshipRepo;
import com.Mapify.Mapify.repository.UserLocationRepo;
import com.Mapify.Mapify.repository.UserLoginRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserLocationRepo userLocationRepo;

    @Autowired
    UserLoginRepo userLoginRepo;

    @Autowired
    JWTService jwtService;

    @Autowired
    FriendshipRepo friendshipRepo;

    public ResponseEntity<List<String>> searchUser(String keyword, String token) {

        String username = jwtService.extractUsername(token);

        List<String> result = userLoginRepo.findByKeyword(keyword);
        result.removeIf(name -> name.equals(username));
        return new ResponseEntity<>(result , HttpStatus.OK);
    }


    public ResponseEntity<String> addSelfLocation(String token, UsersLocationData usersLocationData) {
        String username = jwtService.extractUsername(token);

        Users user = userLoginRepo.findByUsername(username)
                .orElseThrow(()-> new RuntimeException("User Not Found"));

        usersLocationData.setUser(user);
        usersLocationData.setId(user.getId());

        userLocationRepo.save(usersLocationData);

        return new ResponseEntity<>("Success" , HttpStatus.OK);
    }


    public ResponseEntity<String> sendFriendRequest(String token, String receiverUser) {
        String senderUser = jwtService.extractUsername(token);

        if(senderUser.equals(receiverUser)){
            return new ResponseEntity<>("Cannot send request to self",HttpStatus.CONFLICT);
        }

        Users sender = userLoginRepo.findByUsername(senderUser)
                .orElseThrow(()->new RuntimeException("Sender Not Found"));
        Users receiver = userLoginRepo.findByUsername(receiverUser)
                .orElseThrow(()->new RuntimeException("Receiver Not found"));

        Optional<Friendship> f = friendshipRepo.findBySenderAndReceiver(sender,receiver);

        if(f.isPresent() && f.get().getStatus().equals("Pending")){
            return new ResponseEntity<>("Request Already Pending", HttpStatus.CONFLICT);
        }
        Friendship friendship = new Friendship(sender,receiver);
        friendshipRepo.save(friendship);
        return new ResponseEntity<>("Success" , HttpStatus.OK);


    }


    public ResponseEntity<String> acceptFriendRequest(String token, String senderUsername) {
        String receiverUsername = jwtService.extractUsername(token);

        Users sender = userLoginRepo.findByUsername(senderUsername)
                .orElseThrow(()->new RuntimeException("Sender Not Found"));
        Users receiver = userLoginRepo.findByUsername(receiverUsername)
                .orElseThrow(()->new RuntimeException("Receiver Not Found"));


        Friendship friendship = friendshipRepo.findBySenderAndReceiverAndStatus(sender,receiver,"Pending")
                .orElseThrow(()->new RuntimeException("Friendship Not Found"));

        if(friendship.getStatus().equals("Pending")){
            friendship.setStatus("Accepted");
            friendshipRepo.save(friendship);
        }
        else{
            throw new RuntimeException("FriendShip not Pending");
        }

        return new ResponseEntity<>("Success" , HttpStatus.OK);

    }


    public ResponseEntity<List<String>> getPendingRequest(String token) {
        String receiverUsername = jwtService.extractUsername(token);

        Users receiverUser = userLoginRepo.findByUsername(receiverUsername)
                .orElseThrow(()->new RuntimeException("User Not Found"));

        List<Friendship> friendshipList = friendshipRepo.findByReceiverAndStatus(receiverUser,"Pending");

        List<String> result = friendshipList.stream()
                .map(f -> f.getSender().getUsername())
                .toList();

        return new ResponseEntity<>(result , HttpStatus.OK);

    }


    public ResponseEntity<List<String>> getMyRequest(String token) {
        String username = jwtService.extractUsername(token);

        Users user = userLoginRepo.findByUsername(username)
                .orElseThrow(()->new RuntimeException("Username Not Found"));

        List<Friendship> friendshipList = friendshipRepo.findBySenderAndStatus(user, "Pending");

        List<String> result = friendshipList.stream()
                .map(f->f.getReceiver().getUsername())
                .toList();

        return new ResponseEntity<>(result,HttpStatus.OK);



    }

    @Transactional
    public ResponseEntity<String> rejectRequest(String token, String senderUsername) {
        String receiverUsername = jwtService.extractUsername(token);

        Users sender = userLoginRepo.findByUsername(senderUsername)
                .orElseThrow(()-> new RuntimeException("Sender Not found"));

        Users receiver = userLoginRepo.findByUsername(receiverUsername)
                .orElseThrow(()->new RuntimeException("Receiver Not Found"));

        friendshipRepo.deleteBySenderAndReceiverAndStatus(sender,receiver,"Pending");
        return new ResponseEntity<>("Success" , HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> cancelRequest(String token, String receiverUsername) {
        String senderUsername = jwtService.extractUsername(token);
        Users sender = userLoginRepo.findByUsername(senderUsername)
                .orElseThrow(()->new RuntimeException("Sender Not Found"));
        Users receiver = userLoginRepo.findByUsername(receiverUsername)
                .orElseThrow(()->new RuntimeException("Receiver Not Found"));

        friendshipRepo.deleteBySenderAndReceiverAndStatus(sender,receiver,"Pending");
        return new ResponseEntity<>("Success" , HttpStatus.OK);
    }


    public ResponseEntity<List<String>> getFriendsList(String token) {
        String senderUsername = jwtService.extractUsername(token);
        Users sender = userLoginRepo.findByUsername(senderUsername)
                .orElseThrow(()->new RuntimeException("Not Found"));

        List<Friendship> friendshipList = friendshipRepo.findBySenderAndStatus(sender,"Accepted");

        List<String> result = friendshipList.stream()
                .map(f -> f.getReceiver().getUsername())
                .toList();
        return new ResponseEntity<>(result,HttpStatus.OK);

    }


    public ResponseEntity<UsersLocationData> getLocation(String token, String username2) {
        String username1 = jwtService.extractUsername(token);

        Users user1 = userLoginRepo.findByUsername(username1)
                .orElseThrow(()->new RuntimeException("Not Found"));
        Users user2 = userLoginRepo.findByUsername(username2)
                .orElseThrow(()->new RuntimeException("Not Found"));

        Optional<Friendship> f = friendshipRepo.findBySenderAndReceiverAndStatus(user1, user2, "Accepted" );

        if(f.isPresent()){
            UsersLocationData usersLocationData = userLocationRepo.findById(user2.getId())
                    .orElseThrow(()-> new RuntimeException("Location Not Available"));
            return new ResponseEntity<>(usersLocationData, HttpStatus.OK);

        }
        return new ResponseEntity<>(null , HttpStatus.NOT_FOUND);
    }


    @Transactional
    public ResponseEntity<String> deleteFriendship(String token, String username2) {
        String usernam1 = jwtService.extractUsername(token);

        Users sender = userLoginRepo.findByUsername(usernam1)
                .orElseThrow(()->new RuntimeException("Sender Not Found"));
        Users receiver = userLoginRepo.findByUsername(username2)
                .orElseThrow(()->new RuntimeException("Receiver Not found"));

        Optional<Friendship> f1 = friendshipRepo.findBySenderAndReceiverAndStatus(sender , receiver,"Accepted");

        Optional<Friendship> f2 = friendshipRepo.findBySenderAndReceiverAndStatus(receiver , sender,"Accepted");
        f1.ifPresent(f -> friendshipRepo.delete(f));
        f2.ifPresent(f -> friendshipRepo.delete(f));
        return new ResponseEntity<>("Success" , HttpStatus.OK);


    }


    public ResponseEntity<List<String>> getMyViewersList(String token) {
        String username = jwtService.extractUsername(token);

        Users user = userLoginRepo.findByUsername(username)
                .orElseThrow(()->new RuntimeException("User Not Found"));

        List<Friendship> friendships = friendshipRepo.findByReceiverAndStatus(user,"Accepted");

        List<String> result = friendships.stream()
                .map(f-> f.getSender().getUsername())
                .toList();
        return new ResponseEntity<>(result,HttpStatus.OK);

    }


    public ResponseEntity<String> deleteMyViewer(String token, String username2) {
        String username1 = jwtService.extractUsername(token);

        Users user1 = userLoginRepo.findByUsername(username1)
                .orElseThrow(()->new RuntimeException("User Not Found"));
        Users user2 = userLoginRepo.findByUsername(username2)
                .orElseThrow(()->new RuntimeException("User Not Found"));

        friendshipRepo.deleteBySenderAndReceiverAndStatus(user2,user1,"Accepted");
        return new ResponseEntity<>("Success",HttpStatus.OK);
    }


}
