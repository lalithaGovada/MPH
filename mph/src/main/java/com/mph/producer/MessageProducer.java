package com.mph.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer implements IMessageProducer {

    @Autowired
    private KafkaTemplate<String, String> template;

    @Value("${app.props.kafka.topic.name}")
    private String topic;

    @Override
    public void sendMessage(String message) {
        template.send(topic, message);
    }
}
