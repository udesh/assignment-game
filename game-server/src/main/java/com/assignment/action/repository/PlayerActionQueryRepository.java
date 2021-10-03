package com.assignment.action.repository;

import com.assignment.action.PlayerAction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerActionQueryRepository extends MongoRepository<PlayerAction, String> {

    PlayerAction findByPlayerNameAndAction(String name, String action);
}
