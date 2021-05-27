package it.polimi.ingsw.network.message.from_client;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.server.controller.ServerController;

import java.io.Serializable;

public class DropLeaderCardMessage implements Message<ServerController>, Serializable {

    private static final long serialVersionUID = 1L;
    private final String cardId;

    public DropLeaderCardMessage(String id) {
        this.cardId = id;
    }

    public void execute(ServerController controller) {
        controller.dropLeaderCard(cardId);
    }
}
