package it.polimi.ingsw.network;

public class Message {
    MessageType type;       // message header
    String currentPlayer;   // player username
    Object payload;         // message payload

    public Message(MessageType type, String currentPlayer, Object payload) {
        this.type = type;
        this.currentPlayer = currentPlayer;
        this.payload = payload;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }
}
