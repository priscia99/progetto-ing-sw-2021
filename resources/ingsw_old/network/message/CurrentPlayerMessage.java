package it.polimi.ingsw_old.network.message;

import it.polimi.ingsw_old.controller.Controller;

public class CurrentPlayerMessage implements Message {

    private String username;

    public CurrentPlayerMessage(String username) {
        this.username = username;
    }

    @Override
    public void execute(Controller controller) {

    }
}
