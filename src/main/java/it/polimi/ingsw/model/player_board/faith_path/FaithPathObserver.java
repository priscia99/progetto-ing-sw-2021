package it.polimi.ingsw.model.player_board.faith_path;

import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.Lobby;

public class FaithPathObserver implements Observer<Integer> {

    private FaithPath observed;
    private Lobby lobby;

    public FaithPathObserver(FaithPath observed, Lobby lobby) {
        this.observed = observed;
        this.lobby = lobby;
    }

    public FaithPath getObserved() {
        return observed;
    }

    public void setObserved(FaithPath observed) {
        this.observed = observed;
    }

    @Override
    public void update(Integer message) {
        // TODO fill update
        lobby.sendBroadcast(message);
        if (message == 20) {
            // TODO call game
        }
    }
}
