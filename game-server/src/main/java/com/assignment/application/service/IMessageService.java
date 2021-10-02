package com.assignment.application.service;

import com.assignment.application.model.Message;

import java.util.concurrent.ExecutionException;

public interface IMessageService {

    void sendMessageGameTopic(Message message) throws ExecutionException, InterruptedException;

    /**
     * @param sender message sender
     * @param command message command
     * @param gameId game id
     * @return Message
     */
    Message createMessage(String sender, String command, String gameId);
}
