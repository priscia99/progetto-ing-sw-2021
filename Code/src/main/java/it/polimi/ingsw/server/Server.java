package it.polimi.ingsw.server;

import it.polimi.ingsw.model.game.Game;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final int PORT = 8888;
    private ServerSocket serverSocket;
    private ExecutorService executor = Executors.newFixedThreadPool(128);
    // players in queue
    private Map<String, ClientConnection> playersConnections = new HashMap<>();
    // lobbies
    private Map<Game, ArrayList<ClientConnection>> lobbiesConnections = new HashMap<>();


}
