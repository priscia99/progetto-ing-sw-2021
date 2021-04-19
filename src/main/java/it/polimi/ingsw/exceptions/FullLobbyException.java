package it.polimi.ingsw.exceptions;

public class FullLobbyException extends RuntimeException{

    public FullLobbyException(){ super(); }
    public FullLobbyException(String msg){ super(msg); }
}
