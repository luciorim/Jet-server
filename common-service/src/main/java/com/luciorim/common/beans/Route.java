package com.luciorim.common.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Route {
    private String boardName;
    private List<RoutePoint> path = new ArrayList<>();

    public boolean isNotAssigned(){
        return boardName == null;
    }
}
