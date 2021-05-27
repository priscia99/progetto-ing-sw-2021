package it.polimi.ingsw.server;


import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.observer.Observer;

public class ServerMessageEncoder implements Observer<Message<ClientController>> {

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
    public void update(Message<ClientController> message) {
        // this.lobby.sendBroadcast(update);

        this.lobby.sendBroadcast(message);
    }
}
