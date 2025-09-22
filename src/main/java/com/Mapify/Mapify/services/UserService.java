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

import java.util.List;
import java.util.Optional;

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

        Optional<Users> user = userLoginRepo.findByUsername(username);

        usersLocationData.setId(user.get().getId());

        userLocationRepo.save(usersLocationData);

        return new ResponseEntity<>("Success" , HttpStatus.OK);


    }



    public ResponseEntity<String> sendFriendRequest(String token, String receiverUser) {
        String senderUser = jwtService.extractUsername(token);

        if(senderUser.equals(receiverUser)){
            return new ResponseEntity<>("Cannot send request to self",HttpStatus.CONFLICT);
        }

        Optional<Users> sender = userLoginRepo.findByUsername(senderUser);
        Optional<Users> receiver = userLoginRepo.findByUsername(receiverUser);

        Friendship friendship = new Friendship(sender.get() , receiver.get());

        if(sender.isPresent() && receiver.isPresent()){
            Optional<Friendship> f = friendshipRepo.findBySenderAndReceiver(sender.get(),receiver.get());
            if(f.isPresent()){
                return new ResponseEntity<>("Request Already Pending", HttpStatus.CONFLICT);
            }
            friendshipRepo.save(friendship);
            return new ResponseEntity<>("Success" , HttpStatus.OK);
        }
        return new ResponseEntity<>("Not Found" , HttpStatus.NOT_FOUND);

    }







}
