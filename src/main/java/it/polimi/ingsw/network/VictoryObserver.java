package it.polimi.ingsw.network;

import it.polimi.ingsw.server_model.game.Game;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.network.update.Update;

// TODO put me in Game
public class VictoryObserver implements Observer<Update> {

    private Game game;

    public VictoryObserver(Game game) {
        this.game = game;
    }

    @Override
    public void update(Update update) {
        // FIXME Fix update
        /*
        if (option.equals(MessageType.FAITH_PATH_NEXT)
                || option.equals(MessageType.BUY_DEVELOPMENT_CARD)) {
            game.checkVictory();
        }
        */

    }
}
