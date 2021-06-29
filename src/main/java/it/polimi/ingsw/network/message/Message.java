package it.polimi.ingsw.network.message;


import java.io.Serializable;

public abstract class Message<T> implements Serializable {
    private static final long serialVersionUID = 15L;
    protected String playerUsername = "";
    public abstract void execute(T target) throws Exception;

    public void setPlayerUsername(String playerUsername) {
        this.playerUsername = playerUsername;
    }

    public String getPlayerUsername() {
        return playerUsername;
    }
}
