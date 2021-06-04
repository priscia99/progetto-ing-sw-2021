package it.polimi.ingsw.client.model;

import java.util.*;
import java.util.stream.Collectors;

import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.server.model.player_board.faith_path.*;

public class ClientFaithPath extends Observable<ClientFaithPath> {

    private int faithPoints;
    private ArrayList<Boolean> popeFavors;

    public ClientFaithPath() {
        this.faithPoints = 0;
        this.popeFavors = new ArrayList<>();
        this.popeFavors.add(false);
        this.popeFavors.add(false);
        this.popeFavors.add(false);
    }

    public ClientFaithPath(int faithPoints, ArrayList<Boolean> popeFavors) {
        this.faithPoints = faithPoints;
        this.popeFavors = popeFavors;
    }

    public void setFaithPoints(int faithPoints) {
        this.faithPoints = faithPoints;
    }

    public void setPopeFavors(ArrayList<Boolean> popeFavors) {
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

    public void show(){
        notify(this);
    }
}
