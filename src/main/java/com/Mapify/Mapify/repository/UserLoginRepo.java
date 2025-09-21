package com.Mapify.Mapify.repository;


import com.Mapify.Mapify.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserLoginRepo extends JpaRepository<Users , Integer> {

    Optional<Users> findByUsername(String username);


}
