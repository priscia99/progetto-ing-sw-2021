package it.polimi.ingsw_old.exceptions;

public class FullDepotException extends RuntimeException{
    public FullDepotException(){ super(); }
    public FullDepotException(String msg){ super(msg); }
}
