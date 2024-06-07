package com.luciorim.office.contollers;

import com.luciorim.common.beans.Route;
import com.luciorim.office.services.PathService;
import com.luciorim.office.services.WaitingRoutesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/routes")
public class RouteController {

    private final WaitingRoutesService waitingRoutesService;
    private final PathService pathService;;

    @PostMapping(path = "route")
    public void addRoute(@RequestBody List<String> routeLocations){
        Route route = pathService.convertLocationsToRoute(routeLocations);
        waitingRoutesService.addRoute(route);
    }
}
