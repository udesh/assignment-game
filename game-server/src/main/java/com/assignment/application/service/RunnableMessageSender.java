package com.assignment.application.service;

import com.assignment.game.Game;
import com.assignment.game.GameTurn;
import com.assignment.game.repository.GameCommandRepository;
import com.assignment.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;

public class RunnableMessageSender implements Runnable {

    private final Game game;
    private final MessageService messengerService;
    private final GameCommandRepository gameCommandRepository;
    Logger logger = LoggerFactory.getLogger(RunnableMessageSender.class);

    public RunnableMessageSender(Game game, MessageService messengerService,
                                 GameCommandRepository gameCommandRepository) {
        this.game = game;
        this.messengerService = messengerService;
        this.gameCommandRepository = gameCommandRepository;
    }

    @Override
    public void run() {
        for (GameTurn gameTurn : game.getGameTurnList()) {
            if (gameTurn.getMessage() != null && !gameTurn.getMessage().getCommand().isEmpty() &&
                    gameTurn.getMessageStatus().equals(Constants.NOT_PUBLISHED)) {
                try {
                    messengerService.sendMessageGameTopic(gameTurn.getMessage());
                    gameTurn.setMessageStatus(Constants.PUBLISHED);
                    Thread.sleep(1500);
                } catch (ExecutionException |InterruptedException e) {
                    logger.error("Exception on sending message in runnable thread: " + e.getLocalizedMessage());
                } finally {
                   logger.error(" Exception finally clause. Message sending failure.");
                }
            }
        }
        gameCommandRepository.save(game);
    }
}
