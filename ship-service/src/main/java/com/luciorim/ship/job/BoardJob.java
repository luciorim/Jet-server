package com.luciorim.ship.job;

import com.luciorim.common.beans.Board;
import com.luciorim.common.beans.RoutePath;
import com.luciorim.common.messages.BoardStateMessage;
import com.luciorim.common.utils.MessagesConverter;
import com.luciorim.ship.providers.BoardProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@EnableAsync
@EnableScheduling
@RequiredArgsConstructor
public class BoardJob {

    private final BoardProvider boardProvider;

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final MessagesConverter messagesConverter;

    @Scheduled(initialDelay = 0, fixedDelay = 250)
    public void fly(){

        boardProvider.getBoards().stream()
                .filter(Board::hasRoute)
                .forEach(board -> {

                   board.getRoute()
                           .getPath()
                           .stream()
                           .filter(RoutePath::isInProgress)
                           .findFirst()
                           .ifPresent(routePath -> {

                               board.calculatePosition( routePath );
                               routePath.atProgress(board.getSpeed());

                               if (routePath.isDone()) {
                                   board.setLocation(routePath.getTo().getName());
                               }

                           });

                });

    }

    @Async
    @Scheduled(initialDelay = 0, fixedDelay = 1000)
    public void notifyState(){

        boardProvider.getBoards()
                .stream().filter(Board::isBusy)
                .forEach(board -> {

                   Optional<RoutePath> route = board
                           .getRoute()
                           .getPath()
                           .stream()
                           .filter(RoutePath::isInProgress)
                           .findAny();

                   if(route.isEmpty()){
                       List<RoutePath> path = board.getRoute().getPath();

                       board.setLocation(path.getLast().getTo().getName());
                       board.setBusy(false);
                   }

                   kafkaTemplate.sendDefault(messagesConverter.toJson(new BoardStateMessage(board)));

                });

    }

}
