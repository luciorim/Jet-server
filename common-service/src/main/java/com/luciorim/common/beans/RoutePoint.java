package com.luciorim.common.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoutePoint {
    private String name;
    private double coordinateX;
    private double coordinateY;
}
