package it.polimi.ingsw.client.model;

import it.polimi.ingsw.observer.Observable;

import java.util.*;

public class ClientPlayerBoard extends Observable<ClientPlayerBoard> {

    private final ClientFaithPath faithPath;
    private final ClientWarehouse warehouse;
    private final ClientStrongbox strongbox;
    private final ClientLeaderCardDeck clientLeaderCards;
    private final ClientDevelopmentCardDecks developmentCards;

    public ClientPlayerBoard() {
        this.faithPath = new ClientFaithPath();
        this.warehouse = new ClientWarehouse();
        this.strongbox = new ClientStrongbox();
        this.clientLeaderCards = new ClientLeaderCardDeck();
        this.developmentCards = new ClientDevelopmentCardDecks();
    }

    public ClientPlayerBoard(ClientFaithPath faithPath, ClientWarehouse warehouse, ClientStrongbox strongbox,
                             ClientLeaderCardDeck clientLeaderCards, ClientDevelopmentCardDecks developmentCards) {
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

    public ClientLeaderCardDeck getClientLeaderCards() {
        return clientLeaderCards;
    }

    public ClientDevelopmentCardDecks getDevelopmentCards() {
        return developmentCards;
    }

    // TODO: add aggregators of data
}
