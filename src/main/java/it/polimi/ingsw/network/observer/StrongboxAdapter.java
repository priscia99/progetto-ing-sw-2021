package it.polimi.ingsw.network.observer;

import it.polimi.ingsw.model.player_board.storage.Storage;
import it.polimi.ingsw.model.player_board.storage.Strongbox;
import it.polimi.ingsw.model.resource.ResourceStock;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.server.Lobby;

import java.util.ArrayList;

public class StrongboxAdapter extends Observable<Message> implements Observer<Storage> {

    private Storage observed;

    public StrongboxAdapter(Storage observed) {
        this.observed = observed;
    }

    public Storage getObserved() {
        return observed;
    }

    public void setObserved(Storage observed) {
        this.observed = observed;
    }

    @Override
    public void update(Storage message) {
        // TODO: add messageType and create copy
        Message networkMessage = new Message(null, (Strongbox) message);
        notify(networkMessage);
    }
}
