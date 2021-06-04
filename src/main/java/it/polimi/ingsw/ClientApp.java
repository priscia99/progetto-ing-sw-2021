package it.polimi.ingsw;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.model.ClientGame;
import it.polimi.ingsw.client.view.ui.cli.CLI;
import it.polimi.ingsw.client.view.ui.cli.Command;
import it.polimi.ingsw.server.model.game.Game;

import java.io.IOException;
import java.util.ArrayList;

public class ClientApp
{
    public static void main(String[] args){
        ArrayList<Command> commands = new ArrayList<>();
        commands.add(new Command(
                "displayleadercards",
                "show player's leader cards",
                false,
                () -> {}
        ));
        Client client = new Client("127.0.0.1", 12345, new CLI(new ArrayList<Command>()));
        try{
            client.run();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
}
