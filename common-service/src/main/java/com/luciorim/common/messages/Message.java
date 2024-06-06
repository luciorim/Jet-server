package com.luciorim.common.messages;

import com.luciorim.common.enums.EventType;
import com.luciorim.common.enums.Source;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Message {
    protected EventType eventType;
    protected Source source;

    public String getCode(){
        return source.name() + "_" + eventType.name();
    }

}
