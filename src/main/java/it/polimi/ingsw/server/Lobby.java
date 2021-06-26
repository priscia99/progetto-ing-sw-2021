package it.polimi.ingsw.server;

import it.polimi.ingsw.exceptions.FullLobbyException;
import it.polimi.ingsw.exceptions.GameException;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.service_message.GameStartedServiceMessage;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.server.controller.ServerController;
import it.polimi.ingsw.server.model.BackupManager;
import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.server.model.game.Player;
import it.polimi.ingsw.server.model.singleplayer.SinglePlayerGame;
import it.polimi.ingsw.utils.CustomLogger;

import java.util.*;

public class Lobby extends Observable<Message> {
    private final String lobbyId;
    private final int dimension;    // number of players
    private final Map<String, ClientConnection> clientConnectionMap= new HashMap<>();
    private Game game;
    private final BackupManager backupManager;
    private int playerReady = 0;
    ServerMessageDecoder serverMessageDecoder;
    ServerMessageEncoder serverMessageEncoder;

    public Lobby(String lobbyId, int dimension) {
        this.lobbyId = lobbyId;
        this.dimension = dimension;
        this.game = (dimension == 1) ? new SinglePlayerGame() : new Game();
        this.backupManager = new BackupManager(this);
    }

    /**
     * Add a new client's connection the lobby if there is enough space.
     * @param username username of the new player
     * @param clientConnection connection of the new player
     * @throws FullLobbyException if the lobby is complete
     */
    public void addClientConnection(String username, ClientConnection clientConnection) {
        if (this.isFull()) {
            throw new FullLobbyException("Full lobby");
        }
        clientConnectionMap.put(username, clientConnection);
        if(this.isFull()) init();
    }


    /**
     * Remove a player from the lobby.
     * @param clientConnection connection of the player
     */
    public void removeClientConnection(ClientConnection clientConnection) {
        if (!this.isEmpty()) {
            clientConnectionMap.entrySet().removeIf(entry -> entry.getValue().equals(clientConnection));
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

    public void setGame(Game game){
        this.game = game;
        setupObservers();
    }

    public void setupObservers(){
        game.addObserver(serverMessageEncoder);
        game.getMarbleMarket().addObserver(serverMessageEncoder);
        game.getCardMarket().addObserver(serverMessageEncoder);
        game.getPlayers().forEach((player)->{
            Arrays.asList(player.getPlayerBoard().getDevelopmentCardsDecks()).forEach(deck->deck.addObserver(serverMessageEncoder));
            player.getPlayerBoard().getFaithPath().addObserver(serverMessageEncoder);
            player.getPlayerBoard().getLeaderCardsDeck().addObserver(serverMessageEncoder);
            player.getPlayerBoard().getStrongbox().addObserver(serverMessageEncoder);
            player.getPlayerBoard().getWarehouse().addObserver(serverMessageEncoder);
            player.addObserver(serverMessageEncoder);
        });
    }

    public synchronized void setupMVC() {
        playerReady++;
        System.out.println("LOBBY: Un giocatore e' pronto! (" + playerReady + " su " + dimension + ")");
        if (playerReady == dimension) {
            System.out.println("LOBBY: Tutti i giocatori sono pronti!");
            ServerController gameController = new ServerController(game);
            gameController.setBackupManager(backupManager);
            serverMessageDecoder = new ServerMessageDecoder(gameController);
            serverMessageEncoder = new ServerMessageEncoder(this);
            clientConnectionMap.values()
                    .forEach(connection -> {
                        SocketClientConnection socketClientConnection = (SocketClientConnection) connection;
                        socketClientConnection.addObserver(serverMessageDecoder);
                    });
            ArrayList<Player> players = new ArrayList<>();
            clientConnectionMap.keySet()
                    .forEach(username -> {
                        players.add(new Player(username));
                    });
            gameController.tryAction(()->gameController.setupGame(players));
            setupObservers();
            System.out.println("Giving initial assets...");
            gameController.tryAction(gameController::giveInitialAssets);
        }
    }

    public void init(){
        sendBroadcast(new GameStartedServiceMessage(new ArrayList<>(clientConnectionMap.keySet())));
    }
    /**
     * Start a new game.
     */
    public void startGame() {

    }

    /**
     * Send a message to every player in the lobby.
     */
    public void sendBroadcast(Object message) {
        for (String key : clientConnectionMap.keySet()) {
            this.sendUnicast(message, key);
        }
    }

    public void sendUnicast(Object message, String player) {
        System.out.println("Sto inviando un messaggio a " + player);
        clientConnectionMap.get(player).asyncSend(message);
    }

    public boolean contains(String username) {
        return clientConnectionMap.containsKey(username);
    }

    public boolean contains(ClientConnection clientConnection) {
        return clientConnectionMap.containsValue(clientConnection);
    }

    public Player getCurrentPlayer() {
        return this.game.getCurrentPlayer();
    }

    public String getLobbyId() {
        return lobbyId;
    }
}
