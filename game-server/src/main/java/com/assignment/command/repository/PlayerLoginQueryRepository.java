package com.assignment.command.repository;

import com.assignment.command.PlayerLogin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerLoginQueryRepository extends MongoRepository<PlayerLogin, String> {

    PlayerLogin findTopByOrderByCreatedTimeDesc();

}
