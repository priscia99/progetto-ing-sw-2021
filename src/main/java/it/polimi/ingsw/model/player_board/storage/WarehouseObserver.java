package it.polimi.ingsw.model.player_board.storage;

import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.player_board.DevelopmentCardsDeck;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.Lobby;

import java.util.ArrayList;

public class WarehouseObserver implements Observer<ArrayList<DevelopmentCard>> {

    private Warehouse observed;
    private final Lobby lobby;

    public WarehouseObserver(Warehouse observed, Lobby lobby) {
        this.observed = observed;
        this.lobby = lobby;
    }

    public void setObserved(Warehouse observed) {
        this.observed = observed;
    }

    @Override
    public void update(ArrayList<DevelopmentCard> message) {
        // TODO fill update
        lobby.sendBroadcast(message);
    }
}
