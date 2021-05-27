package it.polimi.ingsw_old.network.server;

import it.polimi.ingsw_old.controller.GameController;
import it.polimi.ingsw_old.controller.action.Action;
import it.polimi.ingsw_old.observer.Observer;

public class ServerMessageDecoder implements Observer<Action> {

    private GameController controller;

    public ServerMessageDecoder(GameController controller){
        this.controller = controller;
    }


    @Override
    public void update(Action action) {
        controller.executeAction(action);
    }
}
