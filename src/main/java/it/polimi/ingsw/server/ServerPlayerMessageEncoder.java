package it.polimi.ingsw.server;


import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.observer.Observer;

public class ServerPlayerMessageEncoder implements Observer<Message<ClientController>> {
    private Lobby lobby;
    private String player;

    public ServerPlayerMessageEncoder(Lobby lobby, String player) {
        this.lobby = lobby;
        this.player = player;
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
     * @param message the object to send as payload of the message
     */
    @Override
    public void update(Message<ClientController> message) {
        System.out.println("Sto mandando un: ");
        System.out.println("\t\t" + message.getClass());
//        this.lobby.sendUnicast(message, player);
        this.lobby.sendBroadcast(message);
    }
}
