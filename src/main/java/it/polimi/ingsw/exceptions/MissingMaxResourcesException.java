package it.polimi.ingsw.exceptions;

public class MissingMaxResourcesException extends RuntimeException{
    public MissingMaxResourcesException(){ super(); }
    public MissingMaxResourcesException(String msg){ super(msg); }
}
