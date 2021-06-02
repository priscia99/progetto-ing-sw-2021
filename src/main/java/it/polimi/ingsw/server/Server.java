package it.polimi.ingsw.server;


import it.polimi.ingsw.exceptions.InvalidLobbyException;
import it.polimi.ingsw.utils.CustomLogger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {

    private static final int PORT = 12345;
    private final ServerSocket serverSocket;
    private final Map<String, Lobby> lobbyMap = new HashMap<>();

    public Server() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
    }

    /**
     * Create a new lobby with one player, start the game if the lobby is only one player big.
     * @param dimension number of players in the lobby to consider it complete
     * @param username username of the new player to add
     * @param clientConnection connection of the new player to add
     */
    public synchronized Lobby lobby(int dimension, String username, ClientConnection clientConnection) {
        String lobbyId = this.generateRandomString();
        Lobby tempLobby = new Lobby(lobbyId, dimension);
        tempLobby.addClientConnection(username, clientConnection);
        lobbyMap.put(lobbyId, tempLobby);
        clientConnection.asyncSend(String.format("Joined lobby %s", lobbyId));

        this.startLobbyIsFull(tempLobby);
        return tempLobby;
    }

    /**
     * Add a player to a lobby by a given id.
     * @param lobbyId id of the lobby in which add the player
     * @param username username of the new player to add
     * @param clientConnection connection of the new player to add
     * @throws IllegalArgumentException if the lobby id does not exists on the server
     */
    public synchronized Lobby lobby(String lobbyId, String username, ClientConnection clientConnection) {
        Lobby currLobby = null;
        if (lobbyMap.containsKey(lobbyId)) {
            currLobby = lobbyMap.get(lobbyId);
            currLobby.addClientConnection(username, clientConnection);
            clientConnection.asyncSend(String.format("Joined lobby %s", lobbyId));
            this.startLobbyIsFull(currLobby);
        }
        else {
            throw new InvalidLobbyException("wrong lobby id");
        }
        return currLobby;
    }

       private void startLobbyIsFull(Lobby lobby) {
        if (lobby.isFull()) {
            // start game
            lobby.startGame();
            lobby.sendBroadcast("Lobby completed: GAME STARTED");
            // TODO: send model to views
        }
    }

    /**
     * Remove client's connection from the server lobby in the server. If the lobby become empty, it will remove the
     * lobby from the server.
     * @param clientConnection client connection to remove
     */
    public synchronized void deregisterConnection(ClientConnection clientConnection) {
        lobbyMap.keySet()
                .stream()
                .filter(key -> lobbyMap.get(key).contains(clientConnection))
                .forEach(key -> {
                    Lobby lobby = lobbyMap.get(key);
                    lobby.removeClientConnection(clientConnection);
                    if (lobby.isEmpty()) {
                        lobbyMap.remove(key);
                    }
                });
    }

    /**
     * Remove a target lobby from the server.
     * @param lobby lobby to remove
     */
    public void removeLobby(Lobby lobby) {
        lobbyMap.keySet()
                .stream()
                .filter(key -> lobbyMap.get(key).equals(lobby))
                .forEach(lobbyMap::remove);
    }

    private String generateRandomString() {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789";
        StringBuilder sb = new StringBuilder(7);
        for (int i = 0; i<7; i++) {
            int index =(int)(AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }

    /**
     * Thread-like run function.
     */
    public void run() {
        while (true) {
            try {
                Socket newSocket = serverSocket.accept();
                CustomLogger.getLogger().severe("New client handshaking");
                SocketClientConnection socketClientConnection = new SocketClientConnection(newSocket, this);
                Thread t = new Thread(socketClientConnection);
                t.start();
                CustomLogger.getLogger().severe("Thread started");
            } catch (Exception e) {
                CustomLogger.getLogger().severe("Connection Error!");
                e.printStackTrace();
            }
        }
    }
}