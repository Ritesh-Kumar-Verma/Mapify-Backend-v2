package com.Mapify.Mapify.services;

import com.Mapify.Mapify.model.UserPrinciple;
import com.Mapify.Mapify.model.Users;
import com.Mapify.Mapify.repository.UserLoginRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserLoginRepo userLoginRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> user = userLoginRepo.findByUsername(username);
        if(user.isPresent()){
            return new UserPrinciple(user.get());
        }

        throw new UsernameNotFoundException("User Not Found");
    }



}
