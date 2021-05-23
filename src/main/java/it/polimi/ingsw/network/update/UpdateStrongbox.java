package it.polimi.ingsw.network.update;

import it.polimi.ingsw.view.client_model.ClientStrongbox;
import it.polimi.ingsw.model.player_board.storage.Strongbox;

public class UpdateStrongbox extends  Update{

    public UpdateStrongbox(Object object) {
        super(new ClientStrongbox((Strongbox) object));
    }

    @Override
    public void execute() {
        // TODO fill me
    }
}
