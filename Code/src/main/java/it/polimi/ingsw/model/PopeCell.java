package it.polimi.ingsw.model;

public class PopeCell extends Cell{
    boolean used;

    public PopeCell() {
        this.used = false;
    }

    public boolean isUsed(){
        return used;
    }
}
