package it.polimi.ingsw;

import it.polimi.ingsw_old.network.client.Client;
import it.polimi.ingsw_old.view.ui.cli.CLI;
import it.polimi.ingsw_old.view.ui.cli.Command;

import java.io.IOException;
import java.util.ArrayList;

public class ClientApp
{
    public static void main(String[] args){
        Client client = new Client("127.0.0.1", 12345, new CLI(new ArrayList<Command>()));
        try{
            client.run();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
}
