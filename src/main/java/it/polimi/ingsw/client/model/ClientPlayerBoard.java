package it.polimi.ingsw.client.model;

import it.polimi.ingsw.observer.Observable;

import java.util.*;

public class ClientPlayerBoard extends Observable<ClientPlayerBoard> {

    private final ClientFaithPath faithPath;
    private final ClientWarehouse warehouse;
    private final ClientStrongbox strongbox;
    private final ArrayList<ClientLeaderCard> clientLeaderCards;
    private final ArrayList<ArrayList<ClientDevelopmentCard>> developmentCards;

    public ClientPlayerBoard() {
        this.faithPath = new ClientFaithPath();
        this.warehouse = new ClientWarehouse();
        this.strongbox = new ClientStrongbox();
        this.clientLeaderCards = new ArrayList<>();
        this.developmentCards = new ArrayList<>();
    }

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

    public void addLeaderCard(ClientLeaderCard clientLeaderCard) {
       /*
       this.clientLeaderCards.addCard(clientLeaderCard);
        */

        /*
        public void addCard(ClientLeaderCard clientLeaderCard) {
            this.leaderCards.add(clientLeaderCard);
            notify(clientLeaderCard);
        }
         */

        /*
        public void update(ClientLeaderCard clientLeaderCard) {
            userInterface.displayLeaderCardInsertion();
        }
         */
    }

    // TODO: add aggregators of data
}
