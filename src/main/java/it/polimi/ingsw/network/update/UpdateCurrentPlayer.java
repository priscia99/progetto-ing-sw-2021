package it.polimi.ingsw.network.update;

import it.polimi.ingsw.view.client_model.ClientGame;
import it.polimi.ingsw.model.game.Player;

public class UpdateCurrentPlayer extends Update{

    public UpdateCurrentPlayer(Object object) {
        super(((Player) object).getNickname());
    }

    @Override
    public void execute() {
       // clientGame.setCurrentPlayer((String) getObject());

        /*
        setCurrentPlayer(String player){
            this.currentPlayer = player;
            notify(player);
        }
         */

        /*
        update(player){
            userInterface.showNewTurn(player);
        }
         */
        // TODO Send current player update
    }
}

/*
private UI userInterface
 */