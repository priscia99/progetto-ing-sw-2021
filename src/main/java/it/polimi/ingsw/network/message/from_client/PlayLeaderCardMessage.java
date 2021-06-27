package it.polimi.ingsw.network.message.from_client;


import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.server.controller.ServerController;

import java.io.Serializable;

public class PlayLeaderCardMessage extends Message<ServerController> implements Serializable {

    private static final long serialVersionUID = 1L;
    private final String cardId;

    public PlayLeaderCardMessage(String id) {
        this.cardId = id;
    }

    public void execute(ServerController target) throws Exception {
       target.playLeaderCard(cardId);
    }
}
