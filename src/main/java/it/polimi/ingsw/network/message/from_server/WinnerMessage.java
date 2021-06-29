package it.polimi.ingsw.network.message.from_server;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.server.model.resource.ResourceStock;

import java.io.Serializable;
import java.util.ArrayList;

public class WinnerMessage extends Message<ClientController> implements Serializable {

    private static final long serialVersionUID = 1L;
    private final ArrayList<String> winners;

    public WinnerMessage(ArrayList<String> playersUsername) {
        this.winners = playersUsername;
    }

    public void execute(ClientController target) {
        for(String winnerName : winners){
            target.viewInfoMessage("Winner is: " + winnerName);
        }
        target.endGame();
    }
}
