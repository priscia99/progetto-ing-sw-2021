package it.polimi.ingsw.exceptions;

/**
 * Thrown from action validations methods
 */
public class ValidationException extends Exception {
    public ValidationException(String msg){ super(msg); }
}
