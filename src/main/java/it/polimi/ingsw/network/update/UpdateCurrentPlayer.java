package it.polimi.ingsw.network.update;

import it.polimi.ingsw.view.client_model.ClientGame;
import it.polimi.ingsw.model.game.Player;

public class UpdateCurrentPlayer extends Update{

    public UpdateCurrentPlayer(Object object) {
        super(((Player) object).getNickname());
    }

    @Override
    public void execute() {
        // TODO Send current player update
    }
}

/*
private UI userInterface
 */