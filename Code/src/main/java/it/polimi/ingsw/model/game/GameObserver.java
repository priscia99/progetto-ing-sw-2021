package it.polimi.ingsw.model.game;

import it.polimi.ingsw.observer.Observer;

public class GameObserver implements Observer<Game> {

    private Game observed;

    public GameObserver(Game observed) {
        this.observed = observed;
    }

    public Game getObserved() {
        return observed;
    }

    public void setObserved(Game observed) {
        this.observed = observed;
    }

    @Override
    public void update(Game message) {
        // TODO fill update
    }
}
