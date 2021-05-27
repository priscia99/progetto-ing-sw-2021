package it.polimi.ingsw_old.exceptions;

public class FullLobbyException extends RuntimeException{

    public FullLobbyException(){ super(); }
    public FullLobbyException(String msg){ super(msg); }
}
