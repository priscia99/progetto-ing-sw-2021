package it.polimi.ingsw.network.message.from_client;


import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.server.controller.ServerController;
import it.polimi.ingsw.server.model.card.effect.ProductionEffect;
import it.polimi.ingsw.server.model.resource.ResourcePosition;
import it.polimi.ingsw.server.model.resource.ResourceStock;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class SwapDepotsMessage extends Message<ServerController> implements Serializable {

    private static final long serialVersionUID = 12L;
    int firstIndex;
    int secondIndex;

    public SwapDepotsMessage(int first, int second) {
        this.firstIndex = first;
        this.secondIndex = second;
    }

    /**
     * Execute swap depots action on server
     * @param target
     * @throws Exception
     */
    public void execute(ServerController target) throws Exception {
        target.swapDepots(firstIndex, secondIndex);
    }
}
