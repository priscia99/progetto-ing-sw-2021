package it.polimi.ingsw.network;

import it.polimi.ingsw.network.observer.ObserverWithOption;
import it.polimi.ingsw.network.server.Lobby;

public class MessageEncoder implements ObserverWithOption<Object, MessageType> {

    private Lobby lobby;

    public MessageEncoder(Lobby lobby) {
        this.lobby = lobby;
    }

    public Lobby getLobby() {
        return lobby;
    }

    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }

    /**
     * When the message encoder receive a notify via update() it create a message with the data given and the current player.
     * Then it send the message via broadcast to the lobby.
     * @param object the object to send as payload of the message
     * @param type the type of the message, needed by the receiver to understand what it receives
     */
    @Override
    public void update(Object object, MessageType type) {
        Message message = new Message(type, lobby.getCurrentPlayer().getNickname(), object);
        this.lobby.sendBroadcast(message);
    }
}
