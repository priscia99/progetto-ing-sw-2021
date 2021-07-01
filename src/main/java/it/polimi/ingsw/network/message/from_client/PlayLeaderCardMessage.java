package it.polimi.ingsw.network.message.from_client;


import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.server.controller.ServerController;

import java.io.Serializable;

public class PlayLeaderCardMessage extends Message<ServerController> implements Serializable {

    private static final long serialVersionUID = 38L;
    private final String cardId;

    public PlayLeaderCardMessage(String id) {
        this.cardId = id;
    }

    /**
     * Execute play leader card action on server
     * @param target
     * @throws Exception
     */
    public void execute(ServerController target) throws Exception {
       target.activateLeaderCard(cardId);
    }
}
