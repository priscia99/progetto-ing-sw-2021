package it.polimi.ingsw_old.network.message;

import it.polimi.ingsw_old.controller.Controller;

public class ExceptionMessage implements Message {

    private Exception exception;

    public ExceptionMessage(Exception exception) {
        this.exception = exception;
    }

    @Override
    public void execute(Controller controller) {

    }
}
