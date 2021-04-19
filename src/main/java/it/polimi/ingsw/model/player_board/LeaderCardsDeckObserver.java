package it.polimi.ingsw.model.player_board;

import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.player_board.LeaderCardsDeck;
import it.polimi.ingsw.observer.Observer;

import java.util.ArrayList;

public class LeaderCardsDeckObserver implements Observer<ArrayList<LeaderCard>> {

    private LeaderCardsDeck observed;

    public LeaderCardsDeckObserver(LeaderCardsDeck observed) {
        this.observed = observed;
    }

    public LeaderCardsDeck getObserved() {
        return observed;
    }

    public void setObserved(LeaderCardsDeck observed) {
        this.observed = observed;
    }

    @Override
    public void update(ArrayList<LeaderCard> message) {
        // TODO fill update
    }
}

