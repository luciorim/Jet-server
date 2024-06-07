package com.luciorim.office.services;

import com.luciorim.common.beans.Route;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
@RequiredArgsConstructor
public class WaitingRoutesService {

    private final List<Route> routeList = new ArrayList<>();
    private final Lock lock = new ReentrantLock(true);

    public List<Route> getRoutes() {
        return routeList;
    }

    public void addRoute(Route route) {
        try{
            lock.lock();
            routeList.add(route);
        }finally {
            lock.unlock();
        }
    }

    public void removeRoute(Route route) {
       try{
           lock.lock();
           routeList.removeIf(route::equals);
       }finally {
           lock.unlock();
       }
    }




}
