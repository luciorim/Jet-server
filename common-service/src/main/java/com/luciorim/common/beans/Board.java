package com.luciorim.common.beans;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Board {

    private String name;
    private String location;
    private Route route;
    protected boolean isBusy;
    private double speed;
    private double coordinateX;
    private double coordinateY;
    private double angle;

    public boolean isNotBusy(){
        return !isBusy;
    }

    public boolean hasRoute(){
        return route != null;
    }

    public void calculatePosition(RoutePath routeDirection){
        double t = routeDirection.getProgress()/100.0;

        double toX = (1 - t) * routeDirection.getFrom().getCoordinateX() + t * routeDirection.getTo().getCoordinateX();
        double toY = (1 - t) * routeDirection.getFrom().getCoordinateY() + t * routeDirection.getTo().getCoordinateY();

        double deltaX = this.coordinateX - toX;
        double deltaY = this.coordinateY - toY;

        this.angle = Math.toDegrees(Math.atan2(deltaY, deltaX));
        if(this.angle < 0){
            this.angle += 360;
        }
        this.coordinateX = toX;
        this.coordinateY = toY;
    }
}
