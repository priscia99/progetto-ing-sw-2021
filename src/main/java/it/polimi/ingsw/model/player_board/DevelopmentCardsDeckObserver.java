package it.polimi.ingsw.model.player_board;

import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.observer.Observer;

import java.util.ArrayList;

public class DevelopmentCardsDeckObserver implements Observer<ArrayList<DevelopmentCard>> {

    private DevelopmentCardsDeck observed;

    public DevelopmentCardsDeckObserver(DevelopmentCardsDeck observed) {
        this.observed = observed;
    }

    public void setObserved(DevelopmentCardsDeck observed) {
        this.observed = observed;
    }

    @Override
    public void update(ArrayList<DevelopmentCard> message) {
        // TODO fill update
    }
}
