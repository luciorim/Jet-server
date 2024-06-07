package com.luciorim.common.messages;

import com.luciorim.common.beans.Route;
import com.luciorim.common.enums.EventType;
import com.luciorim.common.enums.Source;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OfficeRouteMessage extends Message {

    private Route route;

    public OfficeRouteMessage() {
        this.source = Source.OFFICE;
        this.eventType = EventType.ROUTE;
    }

    public OfficeRouteMessage(Route val) {
        this();
        this.route = val;
    }
}
