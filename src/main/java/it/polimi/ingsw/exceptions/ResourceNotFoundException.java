package it.polimi.ingsw.exceptions;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(){ super(); }
    public ResourceNotFoundException(String msg){ super(msg); }
}
