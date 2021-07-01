package it.polimi.ingsw.network.message.from_server;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.server.model.resource.ResourceStock;

import java.io.Serializable;
import java.util.ArrayList;

public class VictoryPointsMessage extends Message<ClientController> implements Serializable {

    private static final long serialVersionUID = 36L;
    private final int victoryPoints;

    public VictoryPointsMessage(int points, String playerUsername) {
        this.victoryPoints = points;
        super.setPlayerUsername(playerUsername);
    }

    /**
     * Display victory points of player on client
     * @param target
     */
    public void execute(ClientController target) {
        target.viewInfoMessage(playerUsername + " has " + victoryPoints + " points!");
    }
}
