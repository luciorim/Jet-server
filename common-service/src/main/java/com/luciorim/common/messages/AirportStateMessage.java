package com.luciorim.common.messages;

import com.luciorim.common.beans.Airport;
import com.luciorim.common.enums.EventType;
import com.luciorim.common.enums.Source;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AirportStateMessage extends Message {
    private Airport airport;

    public AirportStateMessage() {
        this.source = Source.AIRPORT;
        this.eventType = EventType.STATE;
    }

    public AirportStateMessage(Airport airport) {
        this();
        this.airport = airport;
    }
}
