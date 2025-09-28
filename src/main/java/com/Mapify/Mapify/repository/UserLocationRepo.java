package com.Mapify.Mapify.repository;

import com.Mapify.Mapify.model.UsersLocationData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserLocationRepo extends JpaRepository<UsersLocationData , Integer> {

}
