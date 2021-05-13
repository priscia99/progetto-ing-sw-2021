package it.polimi.ingsw.network.observer;

import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.player_board.LeaderCardsDeck;
import it.polimi.ingsw.network.server.Lobby;

import java.util.ArrayList;

/**
 * Observer to monitor leader cards deck. It refer to the socket connections (clients).
 */
public class LeaderCardsDeckObserver implements Observer<ArrayList<LeaderCard>> {

    private LeaderCardsDeck observed;
    private final Lobby lobby;

    public LeaderCardsDeckObserver(LeaderCardsDeck observed, Lobby lobby) {
        this.observed = observed;
        this.lobby = lobby;
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
        lobby.sendBroadcast(message);
    }
}

