package it.polimi.ingsw.client.model;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.server.model.resource.*;
import it.polimi.ingsw.server.model.player_board.storage.*;
import it.polimi.ingsw.utils.Pair;

public class ClientWarehouse extends Observable<Pair<ClientWarehouse, String>> implements Serializable {

    private ArrayList<ResourceDepot> resourceDepots;
    private String owner;

    public ClientWarehouse(String owner) {
        this.resourceDepots = new ArrayList<>();
        this.owner = owner;
    }

    public void setResourceDepots(ArrayList<ResourceDepot> resourceDepots) {
        this.resourceDepots = resourceDepots;

        notify(new Pair<>(this, owner));
    }

    public ClientWarehouse(ArrayList<ResourceDepot> resourcePiles, String owner) {
        this.owner = owner;
        this.resourceDepots = resourcePiles;
    }

    public ResourceDepot getResourceDepot(int index) {
        return resourceDepots.get(index);
    }

    public void show(){
        notify(new Pair<>(this, owner));
    }

    public boolean isInitialized(){
        return resourceDepots.size() == 3;
    }
}
