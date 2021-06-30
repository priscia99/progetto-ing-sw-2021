package it.polimi.ingsw.client.model;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.server.model.player_board.faith_path.*;
import it.polimi.ingsw.utils.Pair;

public class ClientFaithPath extends Observable<Pair<ClientFaithPath, String>> implements Serializable {

    private int faithPoints;
    private ArrayList<Boolean> popeFavors;
    private Integer blackCrossPosition;
    private String owner;

    /**
     * Initialize the client faith path with default values
     * @param owner the name of the faith path's owner
     */
    public ClientFaithPath(String owner) {
        this.faithPoints = 0;
        this.popeFavors = new ArrayList<>();
        this.popeFavors.add(false);
        this.popeFavors.add(false);
        this.popeFavors.add(false);
        this.blackCrossPosition = null;
        this.owner = owner;
    }

    /**
     * Initialize the client faith path by passing all parameters
     * @param faithPoints number of faith points
     * @param popeFavors list of all pope favors
     * @param owner the name of the faith path's owner
     */
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
        this.blackCrossPosition = position;
    }

    public int getBlackCrossPosition(){
        return (blackCrossPosition==null) ? 0 : blackCrossPosition;
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
