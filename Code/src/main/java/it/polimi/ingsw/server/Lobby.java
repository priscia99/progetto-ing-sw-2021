package it.polimi.ingsw.server;

import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.Player;

import java.util.ArrayList;
import java.util.List;

public class Lobby {

    private ArrayList<ClientConnection> clientConnections;
    private Game game;

    /**
     * Create new empty Lobby object.
     */
    public Lobby() {
        this.clientConnections = new ArrayList<ClientConnection>();
    }

    /**
     *
     * @return list of clients' connections.
     */
    public ArrayList<ClientConnection> getClientConnections() {
        return clientConnections;
    }

    /**
     *
     * @return game object.
     */
    public Game getGame() {
        return game;
    }

    /**
     * Create game object of the lobby.
     */
    public void createGame() {
        // FIXME develop join mechanic
        Game game = new Game();
    }

    /**
     * Add a client to the lobby.
     * @param clientConnection client's connection to add.
     */
    public void addClientConnection(ClientConnection clientConnection) {
        this.clientConnections.add(clientConnection);
    }

    /**
    public void removeClientConnection(ClientConnection clientConnection) {
        this.clientConnections.remove(clientConnection);
    }
}
