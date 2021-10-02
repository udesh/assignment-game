package com.assignment.application.configuration;

import com.assignment.utils.Constants;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class WebSocketTopicConfig {

    @Bean
    public NewTopic gameTopicCreate() {
        return new NewTopic(Constants.KAFKA_TOPIC, 1, (short) 1);
    }
}
