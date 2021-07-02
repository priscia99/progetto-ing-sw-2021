package it.polimi.ingsw.client;

import java.util.*;
import java.io.*;
import java.net.Socket;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.ClientGame;
import it.polimi.ingsw.client.view.*;
import it.polimi.ingsw.client.view.ui.*;
import it.polimi.ingsw.client.view.ui.cli.CLI;
import it.polimi.ingsw.client.view.ui.gui.GUI;
import it.polimi.ingsw.network.auth_data.*;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.service_message.ServiceMessage;
import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.server.model.player_board.PlayerBoard;
import it.polimi.ingsw.utils.CustomLogger;

public class Client {

    private final UI userInterface;               // type of chosen UI (CLI, GUI)
    private ClientController controller;
    private final ClientMessageEncoder clientMessageEncoder;
    public String myUsername;
    private final String ip;                // server IP
    private final int port;                 // server port
    private boolean active = true;          // check connection status
    private ObjectInputStream socketIn = null;      // input socket
    private ObjectOutputStream socketOut = null;    // output socket

    /**
     * Initialize the client
     * @param ip the ip of the server in which the client has to connect
     * @param port the port of the server in which the client has to connect
     * @param userInterface the selected user interface
     */
    public Client(String ip, int port, UI userInterface){
        this.ip = ip;
        this.port = port;
        this.userInterface = userInterface;
        clientMessageEncoder = new ClientMessageEncoder(this);
    }

    /**
     * Applies the game backup restart the game from a previous state
     * @param backup backup of the client game
     */
    public void applyGameBackup(ClientGame backup){
        backup.setMyProperty(myUsername);
        ArrayList<String> current = new ArrayList<>();
        current.add(backup.getCurrentPlayer());
        setupMVC(current);
        startListening();

        controller.setGame(backup);
        controller.getGame().setMyUsername(myUsername);
        startUI();
        setupGameObservers();
    }

    /**
     * Tells the client controller to start listening
     */
    public void startListening(){
        this.controller.startListening();
    }

    /**
     * Tells the controller to start the User Interface
     */
    public void startUI(){
        this.controller.startUI();
    }

    /**
     * Setting up all views and observers
     */
    private void setupGameObservers(){
        ClientGame game = controller.getGame();
        GameView gameView = new GameView(userInterface);
        game.addObserver(gameView);

        // adding leader cards view observer to all players
        LeaderDeckView leaderDeckView = new LeaderDeckView(userInterface);
        game.getPlayerBoardMap().keySet().forEach(key -> {
            game.getPlayerBoardMap().get(key).getClientLeaderCards().addObserver(leaderDeckView);
        });

        // adding warehouse view observer to all players
        WarehouseView warehouseView = new WarehouseView(userInterface);
        game.getPlayerBoardMap().keySet().forEach(key -> {
            game.getPlayerBoardMap().get(key).getWarehouse().addObserver(warehouseView);
        });

        MarbleMarketView marbleMarketView = new MarbleMarketView(userInterface);
        game.getClientMarbleMarket().addObserver(marbleMarketView);

        CardsMarketView cardsMarketView = new CardsMarketView(userInterface);
        game.getClientCardsMarket().addObserver(cardsMarketView);

        // adding development cards view observer to all players
        DevelopmentDeckView developmentDeckView = new DevelopmentDeckView(userInterface);
        game.getPlayerBoardMap().keySet().forEach(key -> {
            game.getPlayerBoardMap().get(key).getDevelopmentCards().addObserver(developmentDeckView);
        });

        // adding stronbox view observer to all players
        StrongboxView strongboxView = new StrongboxView(userInterface);
        game.getPlayerBoardMap().keySet().forEach(key -> {
            game.getPlayerBoardMap().get(key).getStrongbox().addObserver(strongboxView);
        });

        // adding faithpath view observer to all players
        FaithPathView faithPathView = new FaithPathView(userInterface);
        game.getPlayerBoardMap().keySet().forEach(key -> {
            game.getPlayerBoardMap().get(key).getFaithPath().addObserver(faithPathView);
        });

        ClientPlayerBoardView clientPlayerBoardView= new ClientPlayerBoardView(userInterface);
        game.getPlayerBoardMap().keySet().forEach(key -> {
            game.getPlayerBoardMap().get(key).addObserver(clientPlayerBoardView);
        });
    }

    /**
     * Setting up the Model-View-Controller pattern by creating the client game (model) and setting up the controllers
     * @param players
     */
    public void setupMVC(ArrayList<String> players){

        ClientGame game = new ClientGame(myUsername, players.get(0), players);
        this.controller = new ClientController(game, userInterface);
        userInterface.setController(controller);

        // adding encoder to observers list
        controller.addObserver(clientMessageEncoder);
        setupGameObservers();
    }

    /**
     *
     * @return the active status of the client
     */
    public synchronized boolean isActive(){
        return active;
    }

    /**
     * Sets the status of the client
     * @param active true if client is active, false otherwhise
     */
    public synchronized void setActive(boolean active){
        this.active = active;
    }

    /**
     * Read asynchronously messages from the socket while the client is active.
     * @return reading thread
     * @throws IllegalArgumentException if the message format is unexpected
     * @throws Exception if a generic exception happens shut down the server
     */
    public Thread asyncReadFromSocket(){
        Thread t = new Thread(() -> {
                try {
                    while (isActive()) {
                        Object inputObject = socketIn.readObject();
                        if(inputObject instanceof String){
                            manageAuth((String) inputObject);
                        }
                        else if(inputObject instanceof ServiceMessage){
                            ServiceMessage serviceMessage = (ServiceMessage) inputObject;
                            serviceMessage.execute(this);
                        }
                        else if(inputObject instanceof Message){
                            Message<ClientController> message = (Message<ClientController>) inputObject;
                            message.execute(this.controller);
                        }
                        else{
                            throw new IllegalArgumentException("Unknow, type of received message from client");
                        }
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                    CustomLogger.getLogger().info("There was an error. Application will be closed.");
                    System.exit(0);
                }
        });
        t.start();
        return t;
    }


    /**
     * Thread-like run function which tries to connect to the server, setting up the input and output streams
     * and keeps listening while the server is active
     * @throws IOException
     * @throws NoSuchElementException
     */
    public void run() throws IOException {
        CustomLogger.getLogger().info("Connecting to game server...");
        Socket socket = new Socket(ip, port);
        CustomLogger.getLogger().info("Successfully connected!");
        socketIn = new ObjectInputStream(socket.getInputStream());
        socketOut = new ObjectOutputStream(socket.getOutputStream());
        Scanner stdin = new Scanner(System.in);
        try{
            Thread asyncRead = asyncReadFromSocket();
            asyncRead.join();
        } catch(InterruptedException | NoSuchElementException e){
            CustomLogger.getLogger().info("Connection closed from the client side");
        } finally {
            stdin.close();
            socketIn.close();
            socketOut.close();
            socket.close();
        }
    }

    /**
     * Manages the authentication process to the server by contacting the user interface and requesting to the user
     * the proper authentication data
     * @param authInfo
     */
    public void manageAuth(final String authInfo){
        Thread t = new Thread(() -> {
            String[] command = authInfo.split("#");
            switch (command[0]){
                case "auth_request":
                    if(userInterface instanceof CLI) {
                        AuthData authData = userInterface.requestAuth();
                        this.myUsername = authData.getUsername();
                        sendToSocket(authData);
                    }else if(userInterface instanceof GUI){
                        ((GUI)userInterface).loadAuthScreen(this);
                    }
                    break;
                case "auth_joined":
                    userInterface.displayLobbyJoined(command[1]);
                    break;
                case "auth_created":
                    userInterface.displayLobbyCreated(command[1]);
                    break;
                case "auth_error":
                    userInterface.displayAuthFail(command[1]);
                    break;
                default: break;
            }
        });
        t.start();
    }

    /**
     * Send an object to the socket
     * @param objToSend object to send
     */
    public void sendToSocket(Object objToSend){
        Thread t = new Thread(() -> {
            try {
                socketOut.writeObject(objToSend);
            } catch (IOException e) {
                setActive(false);
            }
        });
        t.start();
    }

    /**
     * Sets the player's username
     * @param myUsername player's username
     */
    public void setMyUsername(String myUsername){
        this.myUsername = myUsername;
    }

    /**
     * Retrieves the player username
     * @return the player username
     */
    public String getMyUsername(){return myUsername;}
}
