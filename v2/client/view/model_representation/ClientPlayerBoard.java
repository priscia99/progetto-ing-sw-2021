package v2.client.view.client_model;

import it.polimi.ingsw.view.client_model.ClientDevelopmentCard;
import it.polimi.ingsw.view.client_model.ClientFaithPath;
import it.polimi.ingsw.view.client_model.ClientLeaderCard;
import it.polimi.ingsw.view.client_model.ClientStrongbox;
import it.polimi.ingsw.view.client_model.ClientWarehouse;

import java.util.ArrayList;

public class ClientPlayerBoard {

    private final it.polimi.ingsw.view.client_model.ClientFaithPath faithPath;
    private final it.polimi.ingsw.view.client_model.ClientWarehouse warehouse;
    private final it.polimi.ingsw.view.client_model.ClientStrongbox strongbox;
    private final ArrayList<it.polimi.ingsw.view.client_model.ClientLeaderCard> clientLeaderCards;
    private final ArrayList<ArrayList<ClientDevelopmentCard>> developmentCards;



    public ClientPlayerBoard(it.polimi.ingsw.view.client_model.ClientFaithPath faithPath, it.polimi.ingsw.view.client_model.ClientWarehouse warehouse, it.polimi.ingsw.view.client_model.ClientStrongbox strongbox,
                             ArrayList<it.polimi.ingsw.view.client_model.ClientLeaderCard> clientLeaderCards, ArrayList<ArrayList<ClientDevelopmentCard>> developmentCards) {
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
