package it.polimi.ingsw_old.exceptions;

public class IllegalResourceException extends RuntimeException{
    public IllegalResourceException(){ super(); }
    public IllegalResourceException(String msg){ super(msg); }
}
