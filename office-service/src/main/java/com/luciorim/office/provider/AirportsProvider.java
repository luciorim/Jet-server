package com.luciorim.office.provider;

import com.luciorim.common.beans.Airport;
import com.luciorim.common.beans.RoutePoint;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Component
@Slf4j
@Getter
@ConfigurationProperties(prefix = "application")
public class AirportsProvider {
    private final List<Airport> ports = new ArrayList<>();

    private Airport findAirportAndRemoveBoard(String board){
        AtomicReference<Airport> airportAtomicReference = new AtomicReference<>();
        ports.stream().filter(airport -> airport.getBoards().contains(board))
                .findFirst()
                .ifPresent(airport -> {
                    airport.removeBoard(board);
                    airportAtomicReference.set(airport);
                });

        return airportAtomicReference.get();
    }

    public Airport getAirportByName(String airportName){
        return ports.stream()
                .filter(airport ->
                        airport
                                .getName()
                                .equals(airportName))
                .findFirst()
                .orElse(new Airport());
    }

    public RoutePoint getRoutePoint(String airportName){
        return new RoutePoint(getAirportByName(airportName));
    }



}
