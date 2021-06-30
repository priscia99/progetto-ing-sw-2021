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

    /**
     * Initialize the client playerboard
     * @param isMine set true if the owner is the same as the player that opens this client instance
     * @param order player order in the client game
     * @param username the name of playerboard's owner
     */
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

    /**
     * Initialize the client playerboard passing all parameters
     * @param faithPath user's faith path
     * @param warehouse user's warehouse
     * @param strongbox user's strongbox
     * @param clientLeaderCards user's leader cards deck
     * @param developmentCards user's development cards deck
     * @param isMine set true if the owner is the same as the player that opens this client instance
     * @param victoryPoints user's victory points
     * @param username the name of the playerboard's owner
     * @param order the player order in the client game
     */
    public ClientPlayerBoard(ClientFaithPath faithPath, ClientWarehouse warehouse, ClientStrongbox strongbox,
                             ClientLeaderCardDeck clientLeaderCards, ClientDevelopmentCardDecks developmentCards,
                             boolean isMine, int victoryPoints, String username, int order) {
        this.username = username;
        this.isMine = isMine;
        this.faithPath = faithPath;
        this.warehouse = warehouse;
        this.strongbox = strongbox;
        this.clientLeaderCards = clientLeaderCards;
        this.developmentCards = developmentCards;
        this.victoryPoints = victoryPoints;
        this.order = order;
    }

    /**
     * Sets the player order in the client game
     * @param order
     */
    public void setOrder(int order) {this.order = order;}

    /**
     * Retrieves the plaeyr order in the client game
     * @return
     */
    public int getOrder(){return order;}

    /**
     *
     * @return the user's faith path
     */
    public ClientFaithPath getFaithPath() {
        return faithPath;
    }

    /**
     *
     * @return the player's warehouse
     */
    public ClientWarehouse getWarehouse() {
        return warehouse;
    }

    /**
     *
     * @return the player's strongbox
     */
    public ClientStrongbox getStrongbox() {
        return strongbox;
    }

    /**
     *
     * @return the player's leader cards deck
     */
    public ClientLeaderCardDeck getClientLeaderCards() {
        return clientLeaderCards;
    }

    /**
     *
     * @param isMine set true if the owner is the same as the player that opens this client instance
     */
    public void setMine(boolean isMine){this.isMine = isMine;}
    public boolean isMine(){return isMine;}
    public String getUsername(){return username;}
    public void refreshStats(){notify(this);}
    public ClientDevelopmentCardDecks getDevelopmentCards() {
        return developmentCards;
    }

    /**
     * Sets the victory points for the player with this playerboard
     * @param points number of victory points
     */
    public void setVictoryPoints(int points){
        this.victoryPoints = points;
        refreshStats();
    }

    /**
     *
     * @return player's victory points
     */
    public int getVictoryPoints() {
        return victoryPoints;
    }
}
