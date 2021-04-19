package it.polimi.ingsw.exceptions;

public class InvalidActionException extends RuntimeException {
    public InvalidActionException(){ super(); }
    public InvalidActionException(String msg){ super(msg); }
}


