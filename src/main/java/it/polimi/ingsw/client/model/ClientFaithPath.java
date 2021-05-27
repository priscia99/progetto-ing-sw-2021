package it.polimi.ingsw.client.model;

import java.util.*;
import java.util.stream.Collectors;
import it.polimi.ingsw.server.model.player_board.faith_path.*;

public class ClientFaithPath {

    private final int faithPoints;
    private final ArrayList<Boolean> popeFavors;

    public ClientFaithPath(FaithPath faithPath) {
        this.faithPoints = faithPath.getFaithPoints();
        popeFavors = Arrays
                .stream(faithPath.getCells())
                .filter(cell -> cell instanceof PopeCell)
                .map(cell -> ((PopeCell) cell).getFavor().isUsed())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ClientFaithPath(int faithPoints, ArrayList<Boolean> popeFavors) {
        this.faithPoints = faithPoints;
        this.popeFavors = popeFavors;
    }

    public int getFaithPoints() {
        return faithPoints;
    }

    public ArrayList<Boolean> getPopeFavors() {
        return popeFavors;
    }

    public boolean getPopeFavor(int index) {
        return popeFavors.get(index);
    }
}
