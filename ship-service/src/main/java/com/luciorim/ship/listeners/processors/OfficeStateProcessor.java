package com.luciorim.ship.listeners.processors;

import com.luciorim.common.messages.BoardStateMessage;
import com.luciorim.common.messages.OfficeStateMessage;
import com.luciorim.common.processor.MessageProcessor;
import com.luciorim.common.utils.MessagesConverter;
import com.luciorim.ship.providers.BoardProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component("OFFICE_STATE")
@RequiredArgsConstructor
public class OfficeStateProcessor implements MessageProcessor<OfficeStateMessage> {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final MessagesConverter messagesConverter;

    private final BoardProvider boardProvider;

    @Override
    public void process(String jsonMessage) {

        boardProvider.getBoards()
                .forEach(board -> {
                    kafkaTemplate.sendDefault(messagesConverter.toJson(new BoardStateMessage(board)));
                });

    }
}
