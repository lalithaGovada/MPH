package com.mph.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mph.model.Price;
import com.mph.producer.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    @Autowired
    private MessageProducer producer;
// <la>it will send message to kafka topics
    @PostMapping("/send")
    public void sendMessage(@RequestBody Price price) throws JsonProcessingException {
        producer.sendMessage(new ObjectMapper().writeValueAsString(price));
    }
}
