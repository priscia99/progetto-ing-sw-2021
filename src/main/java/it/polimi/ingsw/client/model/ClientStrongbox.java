package it.polimi.ingsw.client.model;

import java.util.*;

import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.server.model.resource.*;
import it.polimi.ingsw.server.model.player_board.storage.*;

public class ClientStrongbox extends Observable<ClientStrongbox> {

    private ArrayList<ResourceStock> resourceStocks;

    public ClientStrongbox() {
        this.resourceStocks = new ArrayList<>();
    }

    public void setResourceStocks(ArrayList<ResourceStock> resourceStocks) {
        this.resourceStocks = resourceStocks;
    }

    public ClientStrongbox(ArrayList<ResourceStock> resourcePiles) {
        this.resourceStocks = resourcePiles;
    }

    public ResourceStock gerResourceStock(int index) {
        return resourceStocks.get(index);
    }

    public void show(){
        notify(this);
    }

    public boolean isInitialized(){
        return resourceStocks.size() != 0;
    }
}
