package it.polimi.ingsw.network.update;

public abstract class Update {

    private final Object object;

    public Update(Object object) {
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    public abstract void execute();
}
