package com.assignment.application.service;

import com.assignment.application.model.Message;
import com.assignment.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

@Service
public class MessageService implements IMessageService {

    private final KafkaTemplate<String, Message> kafkaTemplate;

    private final SimpMessagingTemplate simpleMessagingTemplate;

    Logger logger = LoggerFactory.getLogger(MessageService.class);

    /**
     * @param kafkaTemplate kafka template for send message to topic
     * @param simpleMessagingTemplate simple message template to send to web socket
     */
    public MessageService(KafkaTemplate<String, Message> kafkaTemplate,
                          SimpMessagingTemplate simpleMessagingTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.simpleMessagingTemplate = simpleMessagingTemplate;
    }

    /**
     * @param message message to send to kafka topic
     * @throws ExecutionException execution exception on sending the message to kafka topic
     * @throws InterruptedException if writing to topic interrupted this is thrown
     */
    public void sendMessageGameTopic(Message message) throws ExecutionException, InterruptedException {
        kafkaTemplate.send(Constants.KAFKA_TOPIC, message).get();
        logger.info(String.format("Sent to kafka topic : message %s", message));
    }

    public Message createMessage(String sender, String command, String gameId) {
         Message message = new Message();
         message.setSender(sender);
         message.setGameId(gameId);
         message.setCommand(command);
         message.setTimestamp(LocalDateTime.now().toString());
         return message;
    }

    public void sendToWebSocket(String message) {
        simpleMessagingTemplate.convertAndSend(message);
    }

}
