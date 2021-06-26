package it.polimi.ingsw.network.message.from_client;

import it.polimi.ingsw.exceptions.GameException;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.server.controller.ServerController;
import it.polimi.ingsw.server.model.card.effect.ProductionEffect;
import it.polimi.ingsw.server.model.resource.ResourcePosition;
import it.polimi.ingsw.server.model.resource.ResourceStock;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class SwapDepotsMessage extends Message<ServerController> implements Serializable {

    private static final long serialVersionUID = 1L;
    int firstIndex;
    int secondIndex;

    public SwapDepotsMessage(int first, int second) {
        this.firstIndex = first;
        this.secondIndex = second;
    }

    public void execute(ServerController target) throws GameException {
        target.swapDepots(firstIndex, secondIndex);
    }
}
