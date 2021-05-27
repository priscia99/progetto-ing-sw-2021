package it.polimi.ingsw_old.exceptions;

public class NotFulfilledException extends RuntimeException{

    public NotFulfilledException(){ super(); }
    public NotFulfilledException(String msg){ super(msg); }
}
