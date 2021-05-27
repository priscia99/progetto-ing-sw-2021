package it.polimi.ingsw_old.network.message;

import it.polimi.ingsw_old.controller.Controller;

public interface Message {

    public abstract void execute(Controller controller);
}
