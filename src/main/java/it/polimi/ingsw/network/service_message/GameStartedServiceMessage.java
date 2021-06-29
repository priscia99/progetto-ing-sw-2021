package it.polimi.ingsw.network.service_message;

import it.polimi.ingsw.client.Client;

import java.io.Serializable;
import java.util.ArrayList;

public class GameStartedServiceMessage extends ServiceMessage<Client> implements Serializable {

    private static final long serialVersionUID = 40L;
    private ArrayList<String> players;

    public GameStartedServiceMessage(ArrayList<String> players){
        this.players = players;
    }

    public void execute(Client client){
        client.setupMVC(players);
        client.sendToSocket(new ClientReadyServiceMessage());
    }
}
