package it.polimi.ingsw.network.message.from_server;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.network.message.Message;

import java.io.Serializable;

public class UpdateVictoryPointsMessage extends Message<ClientController> implements Serializable {
    private int points;
    private String username;

    public UpdateVictoryPointsMessage(int points, String username){
        this.points = points;
        this.username = username;
    }


    /**
     * Update victory points on client
     * @param target
     * @throws Exception
     */
    @Override
    public void execute(ClientController target) throws Exception {
        target.updateVictoryPoints(points, username);
    }
}
