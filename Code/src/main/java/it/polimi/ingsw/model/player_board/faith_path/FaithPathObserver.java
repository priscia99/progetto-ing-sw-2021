package it.polimi.ingsw.model.player_board.faith_path;

import it.polimi.ingsw.observer.Observer;

public class FaithPathObserver implements Observer<Integer> {

    private FaithPath observed;

    public FaithPathObserver(FaithPath observed) {
        this.observed = observed;
    }

    public FaithPath getObserved() {
        return observed;
    }

    public void setObserved(FaithPath observed) {
        this.observed = observed;
    }

    @Override
    public void update(Integer message) {
        // TODO fill update
    }
}
