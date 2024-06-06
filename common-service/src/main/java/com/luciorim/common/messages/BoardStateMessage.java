package com.luciorim.common.messages;


import com.luciorim.common.beans.Board;
import com.luciorim.common.enums.EventType;
import com.luciorim.common.enums.Source;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardStateMessage extends Message {
    private Board board;

    public BoardStateMessage() {
        this.source = Source.BOARD;
        this.eventType = EventType.STATE;
    }

    public BoardStateMessage(Board board) {
        this();
        this.board = board;
    }
}
