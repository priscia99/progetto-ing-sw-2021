package it.polimi.ingsw.network.message.from_client;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.server.controller.ServerController;
import it.polimi.ingsw.server.model.resource.ResourcePosition;
import it.polimi.ingsw.server.model.resource.ResourceStock;

import java.io.Serializable;
import java.util.HashMap;

public class BuyDevelopmentCardMessage extends Message<ServerController> implements Serializable {

    private static final long serialVersionUID = 1L;
    private final String cardId;
    private final int deckIndex;
    private final HashMap<ResourcePosition, ResourceStock> toConsume;

    public BuyDevelopmentCardMessage(String cardId, int index, HashMap<ResourcePosition, ResourceStock> toConsume) {
        this.cardId = cardId;
        this.deckIndex = index;
        this.toConsume = toConsume;
    }

    public void execute(ServerController target){
        target.buyDevelopmentCard(cardId, deckIndex, toConsume);
    }
}
