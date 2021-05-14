package it.polimi.ingsw.network.update;

public abstract class Update {

    private final ClientCopy clientCopy;

    public Update(ClientCopy clientCopy) {
        this.clientCopy = clientCopy;
    }

    public ClientCopy getClientCopy() {
        return clientCopy;
    }

    public abstract void execute();
}
