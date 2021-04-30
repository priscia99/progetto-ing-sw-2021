package it.polimi.ingsw.model.player_board;

import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.Lobby;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Observer to monitor leader cards deck. It refer to the socket connections (clients) and the model.
 */
public class DevelopmentCardsDeckObserver implements Observer<ArrayList<DevelopmentCard>> {

    private DevelopmentCardsDeck observed;
    private Lobby lobby;

    public DevelopmentCardsDeckObserver(DevelopmentCardsDeck observed, Lobby lobby) {
        this.observed = observed;
        this.lobby = lobby;
    }

    public void setObserved(DevelopmentCardsDeck observed) {
        this.observed = observed;
    }

    @Override
    public void update(ArrayList<DevelopmentCard> message) {
        // TODO fill update
        lobby.sendBroadcast(message);
        // TODO call Game
    }
}
