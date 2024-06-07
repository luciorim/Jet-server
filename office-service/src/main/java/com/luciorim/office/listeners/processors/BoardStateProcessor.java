package com.luciorim.office.listeners.processors;

import com.luciorim.common.beans.Airport;
import com.luciorim.common.beans.Board;
import com.luciorim.common.beans.Route;
import com.luciorim.common.messages.AirportStateMessage;
import com.luciorim.common.messages.BoardStateMessage;
import com.luciorim.common.processor.MessageProcessor;
import com.luciorim.common.utils.MessagesConverter;
import com.luciorim.office.provider.AirportsProvider;
import com.luciorim.office.provider.BoardsProvider;
import com.luciorim.office.services.WaitingRoutesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component("BOARD_STATE")
@RequiredArgsConstructor
public class BoardStateProcessor implements MessageProcessor<BoardStateMessage> {

    private final MessagesConverter messagesConverter;
    private final KafkaTemplate<String, String> kafkaTemplate;

    private final WaitingRoutesService waitingRoutesService;;
    private final BoardsProvider boardsProvider;
    private final AirportsProvider airportsProvider;


    @Override
    public void process(String jsonMessage){

        var message = messagesConverter.extractMessage(jsonMessage, BoardStateMessage.class);

        Board board = message.getBoard();
        Optional<Board> previousBoard = boardsProvider.getBoard(board.getName());
        Airport airport = airportsProvider.getAirportByName(board.getLocation());

        boardsProvider.addOrUpdateBoard(board);

        if (previousBoard.isPresent() && board.hasRoute() && !previousBoard.get().hasRoute()) {
            Route route = board.getRoute();
            waitingRoutesService.removeRoute(route);
        }

        if (previousBoard.isEmpty() || !board.isBusy() && previousBoard.get().isBusy()) {
            airport.addBoard(board.getName());

            kafkaTemplate.sendDefault(messagesConverter.toJson(new AirportStateMessage(airport)));
        }

    }

}
