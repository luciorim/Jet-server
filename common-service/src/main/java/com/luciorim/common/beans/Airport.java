package com.luciorim.common.beans;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode
public class Airport {
    private String name;
    private List<String> boards = new ArrayList<>();
    private int coordinateX;
    private int coordinateY;

    private void addBoard(String board) {
        int index = boards.indexOf(board);
        if(index == -1){
            boards.add(board);
        }else{
            boards.set(index, board);
        }
    }

    private void removeBoard(String board) {
        boards.remove(board);
    }
}
