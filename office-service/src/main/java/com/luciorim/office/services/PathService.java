package com.luciorim.office.services;

import com.luciorim.common.beans.Route;
import com.luciorim.common.beans.RoutePath;
import com.luciorim.common.beans.RoutePoint;
import com.luciorim.office.provider.AirportsProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PathService {
    private final AirportsProvider airportsProvider;

    public RoutePath makePath(String from, String to) {
        return new RoutePath(airportsProvider.getRoutePoint(from), airportsProvider.getRoutePoint(to), 0 );
    }

    public Route convertLocationsToRoute(List<String> locations) {

        Route route = new Route();

        List<RoutePoint> points = new ArrayList<>();
        List<RoutePath> path = new ArrayList<>();

        locations.forEach(location -> {
            airportsProvider
                    .getPorts()
                    .stream()
                    .filter(airport ->
                            airport.getName().equals(location))
                    .findFirst()
                    .ifPresent(airport -> {
                        points.add(new RoutePoint(airport));
                    });
        });

        for (int i = 0; i < points.size(); i++) {
            path.add(new RoutePath());
        }

        path.forEach(row -> {

            int idx = path.indexOf(row);

            if(row.getFrom() == null){

                row.setFrom(points.get(idx));

                if(points.size() > idx + 1){
                    row.setTo(points.get(idx + 1));
                }else{
                    row.setTo(points.get(idx));
                }

            }
        });

        route.setPath(path);

        return route;
    }
}
