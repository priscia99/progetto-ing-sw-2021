package it.polimi.ingsw;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.model.ClientGame;
import it.polimi.ingsw.client.view.ui.cli.CLI;
import it.polimi.ingsw.client.view.ui.cli.Command;
import it.polimi.ingsw.server.model.game.Game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ClientApp
{
    public static void main(String[] args){
        ArrayList<Command> commands = new ArrayList<>();
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("p", "[player] The name of the player whose content is to be displayed");
        parameters.put("v", "[type] Type of content to be displayed");
        commands.add(new Command(
                "view",
                "displays the content of a certain player",
                false,
                parameters
                )
        );

        Client client = new Client("127.0.0.1", 12345, new CLI(commands));
        try{
            client.run();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
}
