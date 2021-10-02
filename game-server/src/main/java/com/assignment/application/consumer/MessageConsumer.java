package com.assignment.application.consumer;

import com.assignment.application.service.MessageService;
import com.assignment.utils.Constants;
import com.assignment.application.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    private final SimpMessagingTemplate template;

    Logger logger = LoggerFactory.getLogger(MessageConsumer.class);


    /**
     *  This is the message consumer/listener for kafka topic
     * @param template The simple message template
     */
    public MessageConsumer(SimpMessagingTemplate template) {
        this.template = template;
    }


    /**
     *  This is the message consumer/listener for topic and send the
     *  message to the websocket.
     * @param message The model for sending to websocket
     */
    @KafkaListener(
            topics = Constants.KAFKA_TOPIC,
            groupId = Constants.GROUP_ID
    )
    public void consume(Message message) {
        template.convertAndSend("/topic/group", message);
        logger.info(" Sent to game web socket, message: " + message);
    }
}
