package it.polimi.ingsw.network.observer;

import it.polimi.ingsw.model.player_board.faith_path.FaithPath;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.server.Lobby;

public class FaithPathAdapter extends Observable<Message> implements Observer<FaithPath> {

    private FaithPath observed;

    public FaithPathAdapter(FaithPath observed) {
        this.observed = observed;
    }

    public FaithPath getObserved() {
        return observed;
    }

    public void setObserved(FaithPath observed) {
        this.observed = observed;
    }

    @Override
    public void update(FaithPath message) {
        // TODO: add messageType and create copy
        // TODO: add game update
        Message networkMessage = new Message(null, message);
        notify(networkMessage);
    }
}
