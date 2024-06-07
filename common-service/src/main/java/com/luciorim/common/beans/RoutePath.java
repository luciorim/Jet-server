package com.luciorim.common.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoutePath {

    private RoutePoint from;
    private RoutePoint to;
    private double progress;

    public void atProgress(double speed) {
        progress += speed;
        if(progress >= 100) {
            progress = 100;
        }
    }

    public boolean isInProgress(){
        return progress < 100;
    }


    public boolean isDone(){
        return progress == 100;
    }
}
