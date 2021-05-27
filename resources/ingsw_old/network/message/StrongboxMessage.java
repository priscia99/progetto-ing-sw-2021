package it.polimi.ingsw_old.network.message;

import it.polimi.ingsw_old.model.resource.ResourceStock;
import it.polimi.ingsw_old.controller.Controller;

import java.io.Serializable;
import java.util.ArrayList;

public class StrongboxMessage implements Message, Serializable {

    private final ArrayList<ResourceStock> resourceStocks;

    public StrongboxMessage(ArrayList<ResourceStock> resourceStocks) {
        this.resourceStocks = resourceStocks;
    }

    @Override
    public void execute(Controller controller) {

    }
}
