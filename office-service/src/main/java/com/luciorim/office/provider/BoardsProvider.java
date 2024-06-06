package com.luciorim.office.provider;

import com.luciorim.common.beans.Board;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
@Getter
@Slf4j
public class BoardsProvider {

    private final List<Board> boards = new ArrayList<>();
    private final Lock lock = new ReentrantLock(true);

    public Optional<Board> getBoard(String boardName){
        return boards.stream()
                .filter(board -> board.getName().equals(boardName))
                .findFirst();
    }

    public void addOrUpdateBoard(Board board){
        try{

            lock.lock();

            var bd = getBoard(board.getName());
            //if present we should update the condition of board
            if(bd.isPresent()){
                int idx = boards.indexOf(bd.get());
                boards.set(idx, bd.get());
            }else{
                boards.add(board);
            }
        }finally {
            lock.unlock();
        }

    }
}
