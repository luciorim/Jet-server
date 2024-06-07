package com.luciorim.ship.listeners.processors;

import com.luciorim.common.beans.Route;
import com.luciorim.common.messages.OfficeRouteMessage;
import com.luciorim.common.processor.MessageProcessor;
import com.luciorim.common.utils.MessagesConverter;
import com.luciorim.ship.providers.BoardProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("OFFICE_ROUTE")
@RequiredArgsConstructor
public class OfficeRouteProcessor implements MessageProcessor<OfficeRouteMessage> {

    private final MessagesConverter messagesConverter;

    private final BoardProvider boardProvider;

    @Override
    public void process(String jsonMessage) {

        OfficeRouteMessage officeRouteMessage = messagesConverter.extractMessage(jsonMessage, OfficeRouteMessage.class);
        Route route = officeRouteMessage.getRoute();

        boardProvider.getBoards().stream()
                .filter(b -> b.isNotBusy() && route.getBoardName().equals(b.getName()))
                .findFirst()
                .ifPresent(b -> {
                   b.setRoute(route);
                   b.setBusy(true);
                   b.setLocation(null);
                });
    }

}
