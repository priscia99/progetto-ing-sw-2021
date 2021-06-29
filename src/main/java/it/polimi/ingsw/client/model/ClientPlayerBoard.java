package it.polimi.ingsw.client.model;

import it.polimi.ingsw.observer.Observable;

import javax.print.MultiDocPrintService;
import java.io.Serializable;
import java.util.*;

public class ClientPlayerBoard extends Observable<ClientPlayerBoard> implements Serializable {

    private boolean isMine;
    private final ClientFaithPath faithPath;
    private final ClientWarehouse warehouse;
    private final ClientStrongbox strongbox;
    private final ClientLeaderCardDeck clientLeaderCards;
    private final ClientDevelopmentCardDecks developmentCards;
    private int order;
    private String username;
    private int victoryPoints;

    public ClientPlayerBoard(boolean isMine, int order, String username) {
        this.username = username;
        this.order = order;
        this.isMine = isMine;
        this.faithPath = new ClientFaithPath(username);
        this.warehouse = new ClientWarehouse(username);
        this.strongbox = new ClientStrongbox(username);
        this.clientLeaderCards = new ClientLeaderCardDeck(isMine, username);
        this.developmentCards = new ClientDevelopmentCardDecks(username);
        this.victoryPoints = 0;
    }

    public void setOrder(int order) {this.order = order;}
    public int getOrder(){return order;}
    public ClientPlayerBoard(ClientFaithPath faithPath, ClientWarehouse warehouse, ClientStrongbox strongbox,
                             ClientLeaderCardDeck clientLeaderCards, ClientDevelopmentCardDecks developmentCards,
                             boolean isMine, int victoryPoints) {
        this.isMine = isMine;
        this.faithPath = faithPath;
        this.warehouse = warehouse;
        this.strongbox = strongbox;
        this.clientLeaderCards = clientLeaderCards;
        this.developmentCards = developmentCards;
        this.victoryPoints = victoryPoints;
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

    public void setMine(boolean flag){this.isMine = flag;}
    public boolean isMine(){return isMine;}
    public String getUsername(){return username;}
    public void refreshStats(){notify(this);}
    public ClientDevelopmentCardDecks getDevelopmentCards() {
        return developmentCards;
    }

    public void setVictoryPoints(int points){
        this.victoryPoints = points;
        refreshStats();
    }
}
