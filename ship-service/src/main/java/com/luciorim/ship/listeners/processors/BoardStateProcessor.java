package com.luciorim.ship.listeners.processors;

import com.luciorim.common.messages.BoardStateMessage;
import com.luciorim.common.processor.MessageProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("BOARD_STATE")
public class BoardStateProcessor implements MessageProcessor<BoardStateMessage> {

    @Override
    public void process(String jsonMessage) {
        //pass
    }

}
