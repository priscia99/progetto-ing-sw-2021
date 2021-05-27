package it.polimi.ingsw_old.network.message;

import it.polimi.ingsw_old.controller.Controller;

import java.util.ArrayList;

public class FaithPathMessage implements Message {

    private final int faithPoints;
    private final ArrayList<Boolean> popeFavors;

    public FaithPathMessage(int faithPoints, ArrayList<Boolean> popeFavors) {
        this.faithPoints = faithPoints;
        this.popeFavors = popeFavors;
    }

    @Override
    public void execute(Controller controller) {

    }
}
