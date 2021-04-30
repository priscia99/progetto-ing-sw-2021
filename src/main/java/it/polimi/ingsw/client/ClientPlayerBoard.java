package it.polimi.ingsw.client;

import java.util.ArrayList;

public class ClientPlayerBoard {

    private final ClientFaithPath faithPath;
    private final ClientWarehouse warehouse;
    private final ClientStrongbox strongbox;
    private final ArrayList<ClientLeaderCard> clientLeaderCards;
    private final ArrayList<ArrayList<ClientDevelopmentCard>> developmentCards;

    public ClientPlayerBoard(ClientFaithPath faithPath, ClientWarehouse warehouse, ClientStrongbox strongbox,
                             ArrayList<ClientLeaderCard> clientLeaderCards, ArrayList<ArrayList<ClientDevelopmentCard>> developmentCards) {
        this.faithPath = faithPath;
        this.warehouse = warehouse;
        this.strongbox = strongbox;
        this.clientLeaderCards = clientLeaderCards;
        this.developmentCards = developmentCards;
    }

    public ClientFaithPath getFaithPath() {
        return faithPath;
    }

    public ClientWarehouse getWarehouse() {
        return warehouse;
    }

    public ClientStrongbox getStrongbox() {
        return strongbox;
    }

    public ArrayList<ClientLeaderCard> getClientLeaderCards() {
        return clientLeaderCards;
    }

    public ArrayList<ArrayList<ClientDevelopmentCard>> getDevelopmentCards() {
        return developmentCards;
    }

    // TODO: add aggregators of data
}
