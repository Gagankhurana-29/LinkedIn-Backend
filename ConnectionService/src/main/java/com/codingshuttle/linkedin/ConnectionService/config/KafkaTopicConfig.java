package com.codingshuttle.linkedin.ConnectionService.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class KafkaTopicConfig {


    @Bean
    public NewTopic connectionRequest()
    {
        return new NewTopic("connection-requests-topic",3,(short) 1);
    }

}
