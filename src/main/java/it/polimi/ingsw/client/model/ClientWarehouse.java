package it.polimi.ingsw.client.model;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.server.model.resource.*;
import it.polimi.ingsw.server.model.player_board.storage.*;
import it.polimi.ingsw.utils.Pair;

public class ClientWarehouse extends Observable<Pair<ClientWarehouse, String>> implements Serializable {

    private static final long serialVersionUID = 1500L;
    private ArrayList<ResourceDepot> resourceDepots;
    private String owner;

    /**
     * Initialize a client warehouse
     * @param owner the name of the warehouse's owner
     */
    public ClientWarehouse(String owner) {
        this.resourceDepots = new ArrayList<>();
        this.owner = owner;
    }

    /**
     * Initialize a client warehouse
     * @param resourcePiles list of resource piles
     * @param owner the name of the warehouse's owner
     */
    public ClientWarehouse(ArrayList<ResourceDepot> resourcePiles, String owner) {
        this.owner = owner;
        this.resourceDepots = resourcePiles;
    }

    /**
     * Sets the resource depots of the warebouse
     * @param resourceDepots list of resource depots
     */
    public void setResourceDepots(ArrayList<ResourceDepot> resourceDepots) {
        this.resourceDepots = resourceDepots;

        notify(new Pair<>(this, owner));
    }

    /**
     * Retrieves the resource depot
     * @param index index of the resource debot
     * @return the selected resource depot
     */
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
