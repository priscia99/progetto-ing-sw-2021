package it.polimi.ingsw.network.update;

import it.polimi.ingsw.client_model.ClientWarehouse;
import it.polimi.ingsw.server_model.player_board.storage.Warehouse;

public class UpdateWarehouse extends Update{

    public UpdateWarehouse(Object object) {
        super(new ClientWarehouse((Warehouse) object));
    }

    @Override
    public void execute() {
        // TODO fill me
    }
}
