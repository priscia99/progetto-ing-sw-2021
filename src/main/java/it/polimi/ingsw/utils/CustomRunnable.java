package it.polimi.ingsw.utils;

/**
 * Class used to wrap lambda functions that can throw Exceptions
 */
public interface CustomRunnable {
    void tryRun() throws  Exception;
}
