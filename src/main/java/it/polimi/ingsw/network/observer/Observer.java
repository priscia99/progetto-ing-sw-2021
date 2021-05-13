package it.polimi.ingsw.network.observer;

public interface Observer<T> {

    void update(T message);

}
