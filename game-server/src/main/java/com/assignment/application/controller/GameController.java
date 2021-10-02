package com.assignment.application.controller;

import com.assignment.application.service.MessageService;
import com.assignment.application.model.LoginMessage;
import com.assignment.application.model.Message;
import com.assignment.game.service.GameService;
import com.assignment.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
public class GameController {

    private final MessageService messengerService;

    private final GameService gameService;

    Logger logger = LoggerFactory.getLogger(GameController.class);

    public GameController(MessageService messengerService, GameService gameService) {
        this.messengerService = messengerService;
        this.gameService = gameService;
    }

    @PostMapping(value = "/api/send", consumes = "application/json", produces = "application/json")
    public void sendMessage(@RequestBody Message message) {
        gameService.gamePlay(message.getCommand());
    }

    @PostMapping(value = "/api/login", consumes = "application/json", produces = "application/json")
    public void login(@RequestBody LoginMessage message) {
        String gameIdentifier = gameService.createGame(message.getName(),message.getPlayMode());
        Message gameCanStartMessage = messengerService.createMessage( message.getName(),
                String.format(Constants.GAME_WELCOME_MESSAGE, message.getName()),gameIdentifier);
        try {
            messengerService.sendMessageGameTopic(gameCanStartMessage);
        } catch (InterruptedException | ExecutionException e) {
            logger.error("error on request sending to kafka topic: " + e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }




}
