package com.mph.consumer;

public interface IMessageConsumer {
    void onMessage(String message);
}