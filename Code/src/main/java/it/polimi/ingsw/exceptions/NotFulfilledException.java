package it.polimi.ingsw.exceptions;

public class NotFulfilledException extends RuntimeException{

    public NotFulfilledException(){ super(); }
    public NotFulfilledException(String msg){ super(msg); }
}
