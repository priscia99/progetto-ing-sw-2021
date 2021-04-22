package it.polimi.ingsw.model.player_board.storage;

import it.polimi.ingsw.model.resource.ResourceStock;
import it.polimi.ingsw.observer.Observer;

import java.util.ArrayList;

public class StorageObserver implements Observer<ArrayList<ResourceStock>> {

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
    public void update(ArrayList<ResourceStock> message) {
        // TODO fill update
    }
}
