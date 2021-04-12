package it.polimi.ingsw.model.player_board.storage;

import it.polimi.ingsw.model.resource.ConsumableResource;
import it.polimi.ingsw.observer.Observer;

import java.util.ArrayList;

public class StorageObserver implements Observer<ArrayList<ConsumableResource>> {

    private Storage observed;

    public StorageObserver(Storage observed) {
        this.observed = observed;
    }

    public Storage getObserved() {
        return observed;
    }

    public void setObserved(Storage observed) {
        this.observed = observed;
    }

    @Override
    public void update(ArrayList<ConsumableResource> message) {
        // TODO fill update
    }
}
