package it.polimi.ingsw.network.observer;

import it.polimi.ingsw.model.player_board.storage.Storage;
import it.polimi.ingsw.model.player_board.storage.Warehouse;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.MessageEncoder;

import java.util.ArrayList;

public class WarehouseAdapter extends Observable<Message> implements Observer<Storage> {

    private Warehouse observed;

    public WarehouseAdapter(Warehouse observed) {
        this.observed = observed;
    }

    public Warehouse getObserved() {
        return observed;
    }

    public void setObserved(Warehouse observed) {
        this.observed = observed;
    }

    @Override
    public void update(Storage message) {
        // TODO: add messageType and create copy
      // Message networkMessage = new Message(null, (Warehouse) message);
        //notify(networkMessage);
    }
}
