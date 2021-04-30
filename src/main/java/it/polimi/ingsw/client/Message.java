package it.polimi.ingsw.client;

import java.io.Serializable;

public class Message implements Serializable {

    private final String header;
    private final Object payload;

    public Message(String header, Object payload) {
        this.header = header;
        this.payload = payload;
    }

    public String getHeader() {
        return header;
    }

    public Object getPayload() {
        return payload;
    }
}
