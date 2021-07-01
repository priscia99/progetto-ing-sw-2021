package it.polimi.ingsw.observer;

import java.util.ArrayList;
import java.util.List;

public class Observable<T> {

    protected final List<Observer<T>> observers = new ArrayList<>();

    /**
     * Add observer to list
     * @param observer
     */
    public void addObserver(Observer<T> observer){
        synchronized (observers) {
            observers.add(observer);
        }
    }

    /**
     * Remove observer to list
     * @param observer
     */
    public void removeObserver(Observer<T> observer){
        synchronized (observers) {
            observers.remove(observer);
        }
    }

    /**
     * Calls method update on all observers
     * @param object
     */
    protected void notify(T object){
        synchronized (observers) {
            for(Observer<T> observer : observers){
                observer.update(object);
            }
        }
    }
}
