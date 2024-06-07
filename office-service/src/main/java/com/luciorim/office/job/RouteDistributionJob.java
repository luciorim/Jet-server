package com.luciorim.office.job;

import com.luciorim.common.beans.Airport;
import com.luciorim.common.beans.Board;
import com.luciorim.common.beans.Route;
import com.luciorim.common.beans.RoutePath;
import com.luciorim.common.messages.AirportStateMessage;
import com.luciorim.common.messages.OfficeRouteMessage;
import com.luciorim.common.utils.MessagesConverter;
import com.luciorim.office.provider.AirportsProvider;
import com.luciorim.office.provider.BoardsProvider;
import com.luciorim.office.services.PathService;
import com.luciorim.office.services.WaitingRoutesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class RouteDistributionJob {

    private final PathService pathService;
    private final WaitingRoutesService waitingRoutesService;

    private final BoardsProvider boardsProvider;
    private final AirportsProvider airportsProvider;

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final MessagesConverter messagesConverter;

    @Scheduled(initialDelay = 500, fixedDelay = 2500)
    public void distributeRoutes() {

        waitingRoutesService.getRoutes()
                .stream()
                .filter(Route::isNotAssigned)
                .forEach(route -> {

                    String startLocation = route.getPath()
                            .get(0)
                            .getFrom()
                            .getName();

                    boardsProvider.getBoards().stream()
                            .filter(board -> startLocation.equals(board.getLocation()))
                            .findFirst()
                            .ifPresent(board -> {
                                sendBoardToRoute(route, board);
                            });

                    if (route.isNotAssigned()) {
                        boardsProvider.getBoards().stream()
                                .filter(Board::isNotBusy)
                                .findFirst()
                                .ifPresent(board -> {

                                    String currentLocation = board.getLocation();

                                    if (!currentLocation.equals(startLocation)) {

                                        RoutePath routePath = pathService.makePath(currentLocation, startLocation);
                                        route.getPath().addFirst(routePath);

                                    }
                                });
                    }
                });


    }

    private void sendBoardToRoute(Route route, Board board) {
        route.setBoardName(board.getName());
        Airport airport = airportsProvider.getAirportByName(board.getLocation());

        board.setLocation(null);
        kafkaTemplate.sendDefault(messagesConverter.toJson(new OfficeRouteMessage(route)));
        kafkaTemplate.sendDefault(messagesConverter.toJson(new AirportStateMessage(airport)));

    }

}
