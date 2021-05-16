package it.polimi.ingsw.network.update;

import it.polimi.ingsw.server_model.game.Player;

public class UpdateCurrentPlayer extends Update{
    public UpdateCurrentPlayer(Object object) {
        super(((Player) object).getNickname());
    }

    @Override
    public void execute() {
        // TODO Send current player update
    }
}
