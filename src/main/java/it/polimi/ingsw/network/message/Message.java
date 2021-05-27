package it.polimi.ingsw.network.message;


public interface Message<T> {

    void execute(T target);
}
