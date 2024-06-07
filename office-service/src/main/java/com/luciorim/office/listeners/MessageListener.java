package com.luciorim.office.listeners;

import com.github.benmanes.caffeine.cache.Cache;
import com.luciorim.common.messages.Message;
import com.luciorim.common.processor.MessageProcessor;
import com.luciorim.common.utils.MessageConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageListener {

    private final MessageConverter messageConverter;
    private final Cache<String, WebSocketSession> socketSessionCache;

    @Autowired
    private Map<String, MessageProcessor<? extends Message>> messageProcessors = new HashMap<>();

    @KafkaListener(id = "officeGroupId", topics = "officeRoutes")
    public void kafkaListen(String data){

        sendKafkaMessageToSocket(data);

        String code = messageConverter.extractCode(data);
        try {
            messageProcessors.get(code).process(data);
        }catch (Exception e){
            log.error(e.getLocalizedMessage());
        }
    }

    private void sendKafkaMessageToSocket(String data){

        socketSessionCache.asMap().values().forEach(session -> {
            try {
                if(session.isOpen()){
                    session.sendMessage(new TextMessage(data));
                }
            } catch (IOException e) {
                log.error(e.getLocalizedMessage());
            }

        });
    }




}
