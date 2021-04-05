package it.polimi.ingsw.model.player_board;

import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.effect.ProductionEffect;
import it.polimi.ingsw.model.player_board.faith_path.FaithPath;
import it.polimi.ingsw.model.player_board.storage.Strongbox;
import it.polimi.ingsw.model.player_board.storage.Warehouse;
import it.polimi.ingsw.model.resource.ResourceType;

import java.util.Stack;

public class PlayerBoard {

    private final FaithPath faithPath;
    private final ProductionEffect basicProduction;
    private final DevelopmentCardsDeck[] developmentCardsDecks;
    private final LeaderCardsDeck leaderCardsDeck;
    private final Warehouse warehouse;
    private final Strongbox strongbox;

    public PlayerBoard(FaithPath faithPath, ProductionEffect basicProduction, DevelopmentCardsDeck[] developmentCardsDecks, LeaderCardsDeck leaderCardsDeck, Warehouse warehouse, Strongbox strongbox) {
        this.faithPath = faithPath;
        this.basicProduction = basicProduction;
        this.developmentCardsDecks = developmentCardsDecks;
        this.leaderCardsDeck = leaderCardsDeck;
        this.warehouse = warehouse;
        this.strongbox = strongbox;
    }

    public FaithPath getFaithPath() {
        return faithPath;
    }

    public ProductionEffect getBasicProduction() {
        return basicProduction;
    }

    public DevelopmentCardsDeck[] getDevelopmentCardsDecks() {
        return developmentCardsDecks;
    }

    public LeaderCardsDeck getLeaderCardsDeck() {
        return leaderCardsDeck;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public Strongbox getStrongbox() {
        return strongbox;
    }

    public void addToDepot(int depot, ResourceType resourceType) {
        warehouse.addToDepot(depot, resourceType);
    }

    public void removeFromDepot(int depot) {
        warehouse.removeFromDepot(depot);
    }
}
