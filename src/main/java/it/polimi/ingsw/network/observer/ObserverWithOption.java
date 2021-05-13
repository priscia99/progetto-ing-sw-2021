package it.polimi.ingsw.network.observer;

public interface ObserverWithOption<T, V> {

    void update(T message, V option);

}
