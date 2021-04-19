package it.polimi.ingsw.exceptions;

public class EmptyDeckException extends RuntimeException{
    public EmptyDeckException(){ super(); }
    public EmptyDeckException(String msg){ super(msg); }
}
