package it.polimi.ingsw.model.player_board.storage;

import it.polimi.ingsw.client.Message;
import it.polimi.ingsw.model.resource.ResourceStock;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.Lobby;

import java.util.ArrayList;

public class StrongboxObserver implements Observer<ArrayList<ResourceStock>> {

    private Storage observed;
    private final Lobby lobby;

    public StrongboxObserver(Storage observed, Lobby lobby) {
        this.observed = observed;
        this.lobby = lobby;
    }

    public Storage getObserved() {
        return observed;
    }

    public void setObserved(Storage observed) {
        this.observed = observed;
    }

    @Override
    public void update(ArrayList<ResourceStock> message) {
        // TODO fill update
        lobby.sendBroadcast(message);
    }
}
