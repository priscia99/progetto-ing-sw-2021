package it.polimi.ingsw.model.player_board.storage;

import it.polimi.ingsw.exceptions.EmptyDepotException;
import it.polimi.ingsw.exceptions.FullDepotException;
import it.polimi.ingsw.exceptions.IllegalResourceException;
import it.polimi.ingsw.model.resource.ResourceType;
import it.polimi.ingsw.observer.Observable;

import java.util.ArrayList;

public class Warehouse {

    private Depot[] depots;

    public Warehouse() {
        this.depots = new Depot[3];
        this.depots[0] = new Depot(1);
        this.depots[1] = new Depot(2);
        this.depots[2] = new Depot(3);
    }

    public Depot[] getDepots() {
        return depots;
    }

    public boolean isFull() {
        for(Depot d : depots){
            if (!d.isFull()) return false;
        }
        return true;
    }

    public void addToDepot(int depotIndex, ResourceType resourceType) {
        try{
            depots[depotIndex].addResource(resourceType);
        }
        catch (IllegalResourceException e) {
            e.printStackTrace();
            // TODO: Manage code behaviour when player is trying to add a resource of a wrong type in a warehouse depot
        }
        catch (FullDepotException e) {
            e.printStackTrace();
            // TODO: Manage code behaviour when player is trying to add a resource in a full warehouse depot
        }
    }

    public void removeConsumedResources(){
        for(Depot toClean : depots) {
            toClean.removeConsumedResources();
        }
    }


    // TODO is it useful ???
    public void removeFromDepot(int depotIndex) {
        try{
            depots[depotIndex].removeResource();
        }
        catch (EmptyDepotException e) {
            e.printStackTrace();
            // TODO: Manage code bahviour when player is trying to remove a resource from an empty warehouse depot
        }
    }
}
