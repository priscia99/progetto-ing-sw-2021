package it.polimi.ingsw_old.exceptions;

public class EmptyDeckException extends RuntimeException{
    public EmptyDeckException(){ super(); }
    public EmptyDeckException(String msg){ super(msg); }
}
