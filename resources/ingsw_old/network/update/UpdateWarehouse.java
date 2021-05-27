package it.polimi.ingsw_old.network.update;

import it.polimi.ingsw_old.view.client_model.ClientWarehouse;
import it.polimi.ingsw_old.model.player_board.storage.Warehouse;

public class UpdateWarehouse extends Update{

    public UpdateWarehouse(Object object) {
        super(new ClientWarehouse((Warehouse) object));
    }

    @Override
    public void execute() {
        // TODO fill me
    }
}
