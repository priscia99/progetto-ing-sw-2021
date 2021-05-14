package it.polimi.ingsw.network;

public class Message {
    String currentPlayer = null;   // player username
    Object payload = null;         // message payload

    public Message(String currentPlayer, Object payload) {
        this.currentPlayer = currentPlayer;
        this.payload = payload;
    }

    public Message(Object payload) {
        this.payload = payload;
        this.currentPlayer = null;
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
