package com.mph.consumer;

import com.mph.helper.MessageProcessHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer implements IMessageConsumer {

    @Autowired
    private MessageProcessHelper messageProcessHelper;

    @Override
    @KafkaListener(topics = "${app.props.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void onMessage(String message) {
        messageProcessHelper.process(message);
    }
}
