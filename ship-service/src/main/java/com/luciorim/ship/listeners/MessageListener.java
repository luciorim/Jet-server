package com.luciorim.ship.listeners;

import com.luciorim.common.messages.Message;
import com.luciorim.common.processor.MessageProcessor;
import com.luciorim.common.utils.MessagesConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageListener {

    private final MessagesConverter messagesConverter;

    @Autowired
    private Map<String, MessageProcessor<? extends Message>> messageProcessors = new HashMap<>();

    @KafkaListener(id = "boardId", topics = "officeRoutes")
    public void radarListener(String message) {

        String code = messagesConverter.extractCode(message);

        try {
            messageProcessors.get(code).process(message);
        }catch (Exception e) {
            log.error("Code: " + code + ", " + e.getLocalizedMessage());
        }
    }
}
