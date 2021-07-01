package it.polimi.ingsw;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.JavaFXClient;
import it.polimi.ingsw.client.model.ClientGame;
import it.polimi.ingsw.client.view.ui.cli.CLI;
import it.polimi.ingsw.client.view.ui.cli.Command;
import it.polimi.ingsw.server.model.game.Game;
import javafx.application.Application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Client application launcher
 */
public class ClientApp
{
    private static String ip;
    private static int port;

    /**
     * Start selected type of client, connecting to selected address
     * @param selection Type of UI
     * @param ip IP address to connect to
     * @param port Port of server
     * @throws Exception
     */
    public static void main(String selection, String ip, int port) throws Exception {
        ClientApp.ip = ip;
        ClientApp.port = port;
        switch(selection){
            case "cli" -> startCLIClient();
            case "gui" -> startGUIClient();
            default -> throw new Exception("Invalid UI type!");
        }

    }

    /**
     * Start a CLI base client
     */
    private static void startCLIClient(){
        Client client = new Client(ClientApp.ip, ClientApp.port, new CLI());
        try{
            client.run();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    /**
     * Start a GUI based client
     */
    private static void startGUIClient(){
        JavaFXClient.setIp(ClientApp.ip);
        Application.launch(JavaFXClient.class);
    }

}

