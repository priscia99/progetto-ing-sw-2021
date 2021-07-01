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

public class ClientApp
{
    private static String ip;
    private static int port;

    public static void main(String selection, String ip, int port) throws Exception {
        ClientApp.ip = ip;
        ClientApp.port = port;
        switch(selection){
            case "cli" -> startCLIClient();
            case "gui" -> startGUIClient();
            default -> throw new Exception("Invalid UI type!");
        }

    }

    private static void startCLIClient(){
        Client client = new Client(ClientApp.ip, ClientApp.port, new CLI());
        try{
            client.run();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    private static void startGUIClient(){
        JavaFXClient.setIp(ClientApp.ip);
        Application.launch(JavaFXClient.class);
    }

}

