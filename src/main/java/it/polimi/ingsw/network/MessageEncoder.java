package it.polimi.ingsw.network;

import it.polimi.ingsw.network.observer.Observer;
import it.polimi.ingsw.network.server.Lobby;

public class MessageEncoder implements Observer<Message> {

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
    public void update(Message message) {
        this.lobby.sendBroadcast(message);
    }
}
