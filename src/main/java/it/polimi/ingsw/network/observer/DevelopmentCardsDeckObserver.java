package it.polimi.ingsw.network.observer;

import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.player_board.DevelopmentCardsDeck;
import it.polimi.ingsw.network.server.Lobby;

import java.util.ArrayList;

/**
 * Observer to monitor leader cards deck. It refer to the socket connections (clients) and the model.
 */
public class DevelopmentCardsDeckObserver implements Observer<ArrayList<DevelopmentCard>> {

    private Lobby lobby;

    public DevelopmentCardsDeckObserver(DevelopmentCardsDeck observed, Lobby lobby) {
        this.lobby = lobby;
    }

    @Override
    public void update(ArrayList<DevelopmentCard> message) {
        // TODO fill update
        lobby.sendBroadcast(message);
        // TODO call Game
    }
}
