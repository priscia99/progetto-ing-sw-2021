package it.polimi.ingsw.network.update;

import it.polimi.ingsw.view.client_model.ClientFaithPath;
import it.polimi.ingsw.model.player_board.faith_path.FaithPath;

public class UpdateFaithPath extends Update{

    public UpdateFaithPath(Object object) {
        super(new ClientFaithPath(((FaithPath) object)));
    }

    @Override
    public void execute() {
        // TODO fill me
    }
}
