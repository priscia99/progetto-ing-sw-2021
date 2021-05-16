package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.update.Update;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.network.server.Lobby;

public class ServerMessageEncoder implements Observer<Update> {

    private Lobby lobby;

    public ServerMessageEncoder(Lobby lobby) {
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
     */
    @Override
    public void update(Update object) {
        // this.lobby.sendBroadcast(update);

        Message message = new Message(lobby.getCurrentPlayer().getNickname(), object);
        this.lobby.sendBroadcast(message);
    }
}
