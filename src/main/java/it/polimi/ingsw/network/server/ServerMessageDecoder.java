package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.observer.Observer;

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
