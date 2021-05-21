package it.polimi.ingsw.exceptions;

public class ActiveOnlyCommandException extends Exception{
    public ActiveOnlyCommandException(){ super(); }
    public ActiveOnlyCommandException(String msg){ super(msg); }
}
