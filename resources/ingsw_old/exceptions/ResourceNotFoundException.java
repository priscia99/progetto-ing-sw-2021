package it.polimi.ingsw_old.exceptions;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(){ super(); }
    public ResourceNotFoundException(String msg){ super(msg); }
}
