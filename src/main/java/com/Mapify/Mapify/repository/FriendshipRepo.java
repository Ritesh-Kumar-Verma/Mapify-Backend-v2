package com.Mapify.Mapify.repository;

import com.Mapify.Mapify.model.Friendship;
import com.Mapify.Mapify.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FriendshipRepo extends JpaRepository<Friendship , Integer> {
    Optional<Friendship> findBySenderAndReceiver(Users users, Users users1);
}
