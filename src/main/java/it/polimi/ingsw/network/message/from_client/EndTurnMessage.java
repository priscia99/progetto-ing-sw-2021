package it.polimi.ingsw.network.message.from_client;


import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.server.controller.ServerController;

import java.io.Serializable;

public class EndTurnMessage extends Message<ServerController> implements Serializable{

    private static final long serialVersionUID = 27L;

    public EndTurnMessage() {
    }

    /**
     * Executes next turn action on server
     * @param target
     * @throws Exception
     */
    public void execute(ServerController target) throws Exception {
        target.nextTurn();
    }
}
