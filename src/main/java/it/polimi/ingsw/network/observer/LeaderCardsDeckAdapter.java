package it.polimi.ingsw.network.observer;

import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.player_board.LeaderCardsDeck;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.server.Lobby;

import java.util.ArrayList;

/**
 * Observer to monitor leader cards deck. It refer to the socket connections (clients).
 */
public class LeaderCardsDeckAdapter extends Observable<Message> implements Observer<LeaderCardsDeck> {

    private LeaderCardsDeck observed;

    public LeaderCardsDeckAdapter(LeaderCardsDeck observed) {
        this.observed = observed;
    }

    public LeaderCardsDeck getObserved() {
        return observed;
    }

    public void setObserved(LeaderCardsDeck observed) {
        this.observed = observed;
    }

    @Override
    public void update(LeaderCardsDeck message) {
        // TODO: add messageType and create copy
        Message networkMessage = new Message(null, message);
        notify(networkMessage);
    }
}

