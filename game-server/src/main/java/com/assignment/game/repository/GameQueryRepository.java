package com.assignment.game.repository;

import com.assignment.game.Game;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GameQueryRepository extends MongoRepository<Game, String> {

    Game findTopByOrderByCreatedTimeDesc();

}
