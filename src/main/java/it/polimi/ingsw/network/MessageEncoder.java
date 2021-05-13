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
    public void update(Object message, MessageType type) {
        this.lobby.sendBroadcast(message);
    }
}
