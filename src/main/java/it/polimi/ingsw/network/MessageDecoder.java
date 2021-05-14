package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.observer.Observer;

public class MessageDecoder implements Observer<Action> {

    private GameController controller;

    public MessageDecoder(GameController controller){
        this.controller = controller;
    }


    @Override
    public void update(Action action) {
        controller.executeAction(action);
    }
}
