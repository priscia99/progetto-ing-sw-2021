package it.polimi.ingsw.model;

public class Warehouse {
    Depot[] depots;

    public Warehouse() {
        depots = new Depot[3];
    }

    public boolean isFull(){
        return true;
    }

    public void addToDepot(int depot, Resource resource){
        // ...
    }

    public void removeFromRepot(int depot){
        // ...
    }
}
