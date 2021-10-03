package com.assignment.action.repository;

import com.assignment.action.PlayerLogin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerLoginCommandRepository extends MongoRepository<PlayerLogin, String> {
}
