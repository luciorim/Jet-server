package com.luciorim.common.utils;

import com.google.gson.Gson;
import com.luciorim.common.messages.Message;

public class MessageConverter {

    private final Gson gson = new Gson();

    public String extractCode(String data){
        return gson.fromJson(data, Message.class).getCode();
    }

    public <T extends Message> T extractMessage(String data, Class<T> tClass){
        return gson.fromJson(data, tClass);
    }

    public String toJson(Object message){
        return gson.toJson(message);
    }

}
