package it.polimi.ingsw_old.exceptions;

public class UnknownCommandException extends Exception{
    public UnknownCommandException(){ super(); }
    public UnknownCommandException(String msg){ super(msg); }
}
