package it.polimi.ingsw.network.message;


public interface Message<T> {

    public abstract void execute(T controller);
}
