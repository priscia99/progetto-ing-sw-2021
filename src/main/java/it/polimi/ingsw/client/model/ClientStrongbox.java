package it.polimi.ingsw.client.model;

import java.io.Serializable;
import java.util.*;

import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.server.model.resource.*;
import it.polimi.ingsw.server.model.player_board.storage.*;
import it.polimi.ingsw.utils.Pair;

public class ClientStrongbox extends Observable<Pair<ClientStrongbox, String>> implements Serializable {

    private ArrayList<ResourceStock> resourceStocks;
    private String owner;

    public ClientStrongbox(String owner) {
        this.owner = owner;
        this.resourceStocks = new ArrayList<>();
    }
    public ClientStrongbox(ArrayList<ResourceStock> resourcePiles, String owner) {
        this.owner = owner;
        this.resourceStocks = resourcePiles;
    }

    public void setResourceStocks(ArrayList<ResourceStock> resourceStocks) {
        this.resourceStocks = resourceStocks;
        notify(new Pair<>(this, owner));
    }


    public ResourceStock getResourceStock(int index) {
        return resourceStocks.get(index);
    }


    public void show(){
        notify(new Pair<>(this, owner));
    }

    public boolean isInitialized(){
        return resourceStocks.size() != 0;
    }
}
