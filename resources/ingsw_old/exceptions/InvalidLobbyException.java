package it.polimi.ingsw_old.exceptions;

public class InvalidLobbyException extends RuntimeException{
    public InvalidLobbyException(){ super(); }
    public InvalidLobbyException(String msg){ super(msg); }
}