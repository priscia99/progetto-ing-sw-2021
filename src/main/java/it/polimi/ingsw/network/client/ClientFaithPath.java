package it.polimi.ingsw.network.client;

public class ClientFaithPath {

    private final int faithPoints;
    private final boolean[] popeFavors;

    public ClientFaithPath(int faithPoints, boolean[] popeFavors) {
        this.faithPoints = faithPoints;
        this.popeFavors = popeFavors;
    }

    public int getFaithPoints() {
        return faithPoints;
    }

    public boolean[] getPopeFavors() {
        return popeFavors;
    }

    public boolean getPopeFavor(int index) {
        return popeFavors[index];
    }
}
