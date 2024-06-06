package com.luciorim.common.messages;

import com.luciorim.common.enums.EventType;
import com.luciorim.common.enums.Source;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OfficeStateMessage extends Message{
    public OfficeStateMessage() {
        this.source = Source.OFFICE;
        this.eventType = EventType.STATE;
    }
}
