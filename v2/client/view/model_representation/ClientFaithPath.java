package v2.client.view.client_model;

import it.polimi.ingsw.model.player_board.faith_path.FaithPath;
import it.polimi.ingsw.model.player_board.faith_path.PopeCell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

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
