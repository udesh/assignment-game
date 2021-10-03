package com.assignment.application.controller;

import com.assignment.action.PlayerAction;
import com.assignment.action.service.PlayerActionService;
import com.assignment.application.service.MessageService;
import com.assignment.application.model.LoginMessage;
import com.assignment.application.model.Message;
import com.assignment.game.service.GameService;
import com.assignment.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
public class GameController {

    private final MessageService messengerService;

    private final GameService gameService;

    private final PlayerActionService playerActionService;

    Logger logger = LoggerFactory.getLogger(GameController.class);

    public GameController(MessageService messengerService, GameService gameService,
                          PlayerActionService playerActionService) {
        this.messengerService = messengerService;
        this.gameService = gameService;
        this.playerActionService = playerActionService;
    }

    @PostMapping(value = "/api/send", consumes = "application/json", produces = "application/json")
    public void userAction(@RequestBody Message message) {
        PlayerAction playerAction = new PlayerAction(UUID.randomUUID(), message.getSender(),
                                           message.getCommand(), LocalDateTime.now().toString());
        playerActionService.save(playerAction);
        gameService.gamePlay(playerAction);
    }

    @PostMapping(value = "/api/login", consumes = "application/json", produces = "application/json")
    public void login(@RequestBody LoginMessage message) {
        String generatedName;
        if (message.getName() == null || message.getName().isEmpty()) {
            generatedName = Constants.DEFAULT_NAME + gameService.getRandomNumberBetween(1, 100);
        } else {
            generatedName = message.getName();
        }
        playerActionService.createPlayerActionAndSave(generatedName,Constants.LOGIN);
        String gameIdentifier = gameService.createGame(generatedName,message.getPlayMode());
        Message gameCanStartMessage = messengerService.createMessage( Constants.MESSAGE_FROM_GAME,
                String.format(Constants.GAME_WELCOME_MESSAGE, generatedName),gameIdentifier);
        try {
            messengerService.sendMessageGameTopic(gameCanStartMessage);
        } catch (InterruptedException | ExecutionException e) {
            logger.error("error on request sending to kafka topic: " + e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }




}
