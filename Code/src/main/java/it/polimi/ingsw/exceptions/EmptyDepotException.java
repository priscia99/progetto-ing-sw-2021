package it.polimi.ingsw.exceptions;

public class EmptyDepotException extends RuntimeException{
    public EmptyDepotException(){ super(); }
    public EmptyDepotException(String msg){ super(msg); }
}
