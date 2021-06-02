package it.polimi.ingsw.network.message.from_client;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.server.controller.ServerController;

import java.io.Serializable;

public class BuyDevelopmentCardMessage extends Message<ServerController> implements Serializable {

    private static final long serialVersionUID = 1L;
    private final int positionX;
    private final int positionY;
    private final int deckIndex;

    public BuyDevelopmentCardMessage(int posX, int posY, int index) {
        this.positionX = posX;
        this.positionY = posY;
        this.deckIndex = index;
    }

    public void execute(ServerController target){
        target.buyDevelopmentCard(positionX, positionY, deckIndex);
    }
}
