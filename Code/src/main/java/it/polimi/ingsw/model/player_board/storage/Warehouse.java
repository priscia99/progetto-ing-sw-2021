package it.polimi.ingsw.model.player_board.storage;

import it.polimi.ingsw.exceptions.EmptyDepotException;
import it.polimi.ingsw.exceptions.FullDepotException;
import it.polimi.ingsw.exceptions.IllegalResourceException;
import it.polimi.ingsw.model.resource.ResourceType;

import java.util.ArrayList;

public class Warehouse {

    private Depot[] depots;

    public Warehouse() {
        this.depots = new Depot[3];
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
        }catch (IllegalResourceException e){
            // TODO: Manage code behaviour when player is trying to add a resource of a wrong type in a warehouse depot
        }
        catch (FullDepotException e){
            // TODO: Manage code behaviour when player is trying to add a resource in a full warehouse depot
        }
    }

    public void removeFromDepot(int depotIndex) {
        try{
            depots[depotIndex].removeResource();
        }catch (EmptyDepotException e){
            // TODO: Manage code bahviour when player is trying to remove a resource from an empty warehouse depot
        }
    }
}