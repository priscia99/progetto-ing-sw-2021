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

    @Override
    public void update(Object object, MessageType type) {
        // TODO complete
        Message message = new Message(type, object);
        this.lobby.sendBroadcast(message);
    }
}
