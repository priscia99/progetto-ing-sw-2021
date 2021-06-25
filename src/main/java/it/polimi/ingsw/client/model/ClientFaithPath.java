package it.polimi.ingsw.client.model;

import java.util.*;
import java.util.stream.Collectors;

import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.server.model.player_board.faith_path.*;
import it.polimi.ingsw.utils.Pair;

public class ClientFaithPath extends Observable<Pair<ClientFaithPath, String>> {

    private int faithPoints;
    private ArrayList<Boolean> popeFavors;
    private Optional<Integer> blackCrossPosition;
    private String owner;

    public ClientFaithPath(String owner) {
        this.faithPoints = 0;
        this.popeFavors = new ArrayList<>();
        this.popeFavors.add(false);
        this.popeFavors.add(false);
        this.popeFavors.add(false);
        this.blackCrossPosition = Optional.empty();
        this.owner = owner;
    }

    public ClientFaithPath(int faithPoints, ArrayList<Boolean> popeFavors, String owner) {
        this.faithPoints = faithPoints;
        this.owner = owner;
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

    public void setBlackCrossPosition(int position){
        this.blackCrossPosition = Optional.of(position);
    }

    public int getBlackCrossPosition(){
        return blackCrossPosition.orElse(0);
    }

    public ArrayList<Boolean> getPopeFavors() {
        return popeFavors;
    }

    public boolean getPopeFavor(int index) {
        return popeFavors.get(index);
    }

    public void show(){
        notify(new Pair<>(this, owner));
    }

    public void show(boolean toView){
        if(toView) notify(new Pair<>(this, owner));
    }
}
