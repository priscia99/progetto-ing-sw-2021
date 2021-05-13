package it.polimi.ingsw.network.observer;

import java.util.ArrayList;
import java.util.List;

public class ObservableWithOption<T, V> {

    private final List<ObserverWithOption<T, V>> observers = new ArrayList<>();

    public void addObserver(ObserverWithOption<T, V> observer){
        synchronized (observers) {
            observers.add(observer);
        }
    }

    public void removeObserver(ObserverWithOption<T, V> observer){
        synchronized (observers) {
            observers.remove(observer);
        }
    }

    protected void notify(T message, V option){
        synchronized (observers) {
            for(ObserverWithOption<T, V> observer : observers){
                observer.update(message, option);
            }
        }
    }
}
