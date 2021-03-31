package it.polimi.ingsw.model;

public class Depot {
    int capacity;
    Resource resourceType;
    int occupiedSpace;

    public Depot(int capacity) {
        this.capacity = capacity;
        resourceType = null;
        occupiedSpace = 0;
    }

    public void addResource(Resource resource){
        // ...
        occupiedSpace++;
    }

    public boolean isEmpty(){
        return (occupiedSpace == 0);
    }

    public Resource getResourceType() {
        return resourceType;
    }

}


