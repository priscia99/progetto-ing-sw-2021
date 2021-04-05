package it.polimi.ingsw.exceptions;

public class FullDepotException extends RuntimeException{
    public FullDepotException(){ super(); }
    public FullDepotException(String msg){ super(msg); }
}
