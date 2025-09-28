package com.Mapify.Mapify.repository;

import com.Mapify.Mapify.model.Friendship;
import com.Mapify.Mapify.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendshipRepo extends JpaRepository<Friendship , Integer> {
    Optional<Friendship> findBySenderAndReceiver(Users users, Users users1);

    List<Friendship> findByReceiverAndStatus(Users receiverUser, String pending);

    List<Friendship> findBySenderAndStatus(Users user, String pending);

    void deleteBySenderAndReceiverAndStatus(Users sender, Users receiver, String pending);

    Optional<Friendship> findBySenderAndReceiverAndStatus(Users user1, Users user2, String accepted);
}
