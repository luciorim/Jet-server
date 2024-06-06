package com.luciorim.common.processor;

import com.luciorim.common.messages.Message;

public interface MessageProcessor<T extends Message>{
    void process(String jsonMessage);
}
