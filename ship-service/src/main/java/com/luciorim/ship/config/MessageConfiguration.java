package com.luciorim.ship.config;

import com.luciorim.common.utils.MessagesConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageConfiguration {

    @Bean
    public MessagesConverter messagesConverter() {
        return new MessagesConverter();
    }
}
