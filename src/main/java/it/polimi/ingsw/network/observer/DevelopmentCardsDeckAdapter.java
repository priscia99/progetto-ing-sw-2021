package it.polimi.ingsw.network.observer;

import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.player_board.DevelopmentCardsDeck;
import it.polimi.ingsw.model.player_board.storage.Strongbox;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.server.Lobby;

import java.util.ArrayList;

/**
 * Observer to monitor leader cards deck. It refer to the socket connections (clients) and the model.
 */
public class DevelopmentCardsDeckAdapter extends Observable<Message> implements Observer<DevelopmentCardsDeck> {

    private DevelopmentCardsDeck observed;

    public DevelopmentCardsDeckAdapter(DevelopmentCardsDeck observed, Lobby lobby) {
        this.observed = observed;
    }

    public DevelopmentCardsDeck getObserved() {
        return observed;
    }

    public void setObserved(DevelopmentCardsDeck observed) {
        this.observed = observed;
    }

    @Override
    public void update(DevelopmentCardsDeck message) {
        // TODO: add messageType and create copy
        // TODO: add game update
       // Message networkMessage = new Message(null, message);
        // notify(networkMessage);
    }
}
