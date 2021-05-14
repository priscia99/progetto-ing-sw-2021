package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.observer.Observer;

public class MessageDecoder implements Observer<Message> {

    private TurnController turnController;

    public MessageDecoder(TurnController controller){
        this.turnController = controller;
    }


    @Override
    public void update(Message object) {
        // TODO: Fill me
    }

}
