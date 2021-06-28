package it.polimi.ingsw.server;

import it.polimi.ingsw.exceptions.FullLobbyException;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.from_client.EndTurnMessage;
import it.polimi.ingsw.network.message.from_server.ExceptionMessage;
import it.polimi.ingsw.network.message.from_server.GameBackupMessage;
import it.polimi.ingsw.network.message.from_server.SetupMessage;
import it.polimi.ingsw.network.service_message.GameStartedServiceMessage;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.server.controller.ServerController;
import it.polimi.ingsw.server.model.BackupManager;
import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.server.model.game.Player;
import it.polimi.ingsw.server.model.singleplayer.SinglePlayerGame;
import it.polimi.ingsw.utils.CustomLogger;

import java.util.*;

/**
 * Class to model the lobby of player. This object contains the game and manage its life and the communications.
 */
public class Lobby extends Observable<Message> {
    private final String lobbyId;
    private final int dimension;    // number of players
    private final Map<String, ClientConnection> clientConnectionMap= new HashMap<>();
    private Game game;
    private final BackupManager backupManager;
    private int playerReady = 0;
    ServerMessageDecoder serverMessageDecoder;
    ServerMessageEncoder serverMessageEncoder;
    private ArrayList<String> disconnectedUsernames = new ArrayList<>();

    /**
     * Create a new Lobby object with a give id and a given dimension
     * @param lobbyId the unique id of the lobby
     * @param dimension the minimum and maximum number of player needed to start the game
     */
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
        if(this.isFull())throw new FullLobbyException("Full lobby");

        if (!this.disconnectedUsernames.contains(username)) {
            clientConnectionMap.put(username, clientConnection);
            if(this.isFull()) init();
        } else {
            CustomLogger.getLogger().info("["+this.getLobbyId()+"] "+ username + " RECONNECTED!!" );
            disconnectedUsernames.remove(username);
            GameBackupMessage backup;
            try{
                backup = new GameBackupMessage(backupManager.getBackup());
                this.sendUnicast(backup, username);
            } catch (Exception e){
                CustomLogger.getLogger().severe(e.getMessage());
                e.printStackTrace();
            }
        }
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

    /**
     * Test if the lobby is full or not.
     * @return true if the lobby is full (number of players equals to the dimension of the lobby); else false
     */
    public boolean isFull() {
        return clientConnectionMap.size() == dimension;
    }

    /**
     * Test if the lobby is empty or not.
     * @return true if the lobby is empty (number of players equals to 0); else false
     */
    public boolean isEmpty() {
        return clientConnectionMap.size() == 0;
    }

    public void setGame(Game game){
        this.game = game;
        setupObservers();
    }

    /**
     * Setup the observers to get updates from the game (model)
     */
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

    /**
     * Setup the components needed to operate according to the MVC paradigm.
     */
    public synchronized void setupMVC() {
        playerReady++;
        CustomLogger.getLogger().info("["+lobbyId+"]"+" Un giocatore e' pronto! (" + playerReady + " su " + dimension + ")");
        if (playerReady == dimension) {
            CustomLogger.getLogger().info("["+lobbyId+"]"+ " Tutti i giocatori sono pronti!");
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
            CustomLogger.getLogger().info("Giving initial assets...");
            gameController.tryAction(gameController::giveInitialAssets);
        }
    }

    /**
     * Launche the initialization phase.
     */
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

    /**
     * Send a message to a single player of the lobby.
     * @param message the object to send
     * @param player the username of the target player
     */
    public void sendUnicast(Object message, String player) {
        CustomLogger.getLogger().info("[" + lobbyId +"]" + " Sto inviando un messaggio a " + player);
        clientConnectionMap.get(player).asyncSend(message);
    }

    /**
     * Test if the lobby contains a player.
     * @param username the username of the player to test
     * @return true if the player is contained; else false
     */
    public boolean contains(String username) {
        return clientConnectionMap.containsKey(username);
    }

    /**
     * Test if the lobby contains a ClientConnection.
     * @param clientConnection the connection to test
     * @return true if the connection is contained; else false
     */
    public boolean contains(ClientConnection clientConnection) {
        return clientConnectionMap.containsValue(clientConnection);
    }

    public Player getCurrentPlayer() {
        return this.game.getCurrentPlayer();
    }

    public String getLobbyId() {
        return lobbyId;
    }

    public void addDisconnected(String username) {
        this.disconnectedUsernames.add(username);
    }
}
