package it.polimi.ingsw.server;

import it.polimi.ingsw.exceptions.FullLobbyException;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.Player;

import java.util.*;
import java.util.stream.Collectors;

public class Lobby {

    private final int dimension;    // number of players
    private final Map<String, ClientConnection> clientConnectionMap= new HashMap<>();
    private final Game game = new Game();

    public Lobby(int dimension) {
        this.dimension = dimension;
    }

    /**
     * Add a new client's connection the lobby if there is enough space.
     * @param username username of the new player
     * @param clientConnection connection of the new player
     * @throws it.polimi.ingsw.exceptions.FullLobbyException if the lobby is complete
     */
    public void addClientConnection(String username, ClientConnection clientConnection) {
        if (this.isFull()) {
            throw new FullLobbyException("Full lobby");
        }
        clientConnectionMap.put(username, clientConnection);
    }

    /**
     * Remove a player from the lobby.
     * @param username username of the player
     */
    public void removeClientConnection(String username) {
        if (!this.isEmpty()) {
            clientConnectionMap.remove(username);
        }
    }

    /**
     * Remove a player from the lobby.
     * @param clientConnection connection of the player
     */
    public void removeClientConnection(ClientConnection clientConnection) {
        if (!this.isEmpty()) {
            clientConnectionMap.keySet()
                    .stream()
                    .filter(key -> clientConnectionMap.get(key).equals(clientConnection))
                    .forEach(this::removeClientConnection);
        }
    }


    public int getDimension() {
        return dimension;
    }

    public boolean isFull() {
        return clientConnectionMap.size() == dimension;
    }

    public boolean isEmpty() {
        return clientConnectionMap.size() == 0;
    }

    /**
     * Start a new game.
     */
    public void startGame() {
        ArrayList<Player> players = new ArrayList<>();
        clientConnectionMap.keySet()
                .forEach(username -> {
                    players.add(new Player(username));
                });
        game.setup(players);
    }

    /**
     * Send a message to every player in the lobby.
     */
    public void sendBroadcast(Object message) {
        clientConnectionMap.keySet()
                .forEach(key -> {
                    clientConnectionMap.get(key).asyncSend(message);
                });
    }

    public boolean contains(String username) {
        return clientConnectionMap.containsKey(username);
    }

    public boolean contains(ClientConnection clientConnection) {
        return clientConnectionMap.containsValue(clientConnection);
    }
}
