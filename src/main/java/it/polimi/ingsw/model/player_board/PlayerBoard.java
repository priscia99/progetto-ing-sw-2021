package it.polimi.ingsw.model.player_board;

import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.card.effect.ProductionEffect;
import it.polimi.ingsw.model.player_board.faith_path.FaithPath;
import it.polimi.ingsw.model.player_board.storage.Strongbox;
import it.polimi.ingsw.model.player_board.storage.Warehouse;
import it.polimi.ingsw.model.resource.ResourceType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * Class that models the player board. It instantiate a faith path, storages and slots for cards. It take count of the
 * player statistics such as victory points.
 */
public class PlayerBoard {

    private final FaithPath faithPath;
    private final ProductionEffect basicProduction;
    private final DevelopmentCardsDeck[] developmentCardsDecks;
    private final LeaderCardsDeck leaderCardsDeck;
    private final Warehouse warehouse;
    private final Strongbox strongbox;

    /**
     * Create an empty PlayerBoard object.
     */
    public PlayerBoard() {
        this.strongbox = new Strongbox();
        this.warehouse = new Warehouse();
        this.leaderCardsDeck = new LeaderCardsDeck();
        this.developmentCardsDecks = new DevelopmentCardsDeck[3];
        this.basicProduction = ProductionEffect.getBasicProduction();
        this.faithPath = FaithPath.getStandardFaithPath();
    }

    /**
     *
     * @return the faith path object.
     */
    public FaithPath getFaithPath() {
        return faithPath;
    }

    /**
     *
     * @return the basic production effect of the player board (2 generic resources, for 1 generic resource)
     */
    public ProductionEffect getBasicProduction() {
        return basicProduction;
    }

    /**
     *
     * @return return the decks of development cards.
     */
    public DevelopmentCardsDeck[] getDevelopmentCardsDecks() {
        return developmentCardsDecks;
    }

    /**
     *
     * @return return the decks of leader cards.
     */
    public LeaderCardsDeck getLeaderCardsDeck() {
        return leaderCardsDeck;
    }

    /**
     *
     * @return return the warehouse that contains the depots of resources.
     */
    public Warehouse getWarehouse() { return warehouse; }

    /**
     *
     * @return the strongbox of resources.
     */
    public Strongbox getStrongbox() {
        return strongbox;
    }

    /**
     * Add a new leader card to the deck of leader cards.
     * @param leadersToAdd the new leader card object to add.
     */
    public void addToLeaderCardsDeck(List<LeaderCard> leadersToAdd){
        for(LeaderCard card : leadersToAdd){
            this.leaderCardsDeck.addLeader(card);
        }
    }

    /**
     * Test if there is any leader card.
     * @return true if there is at least a leader card in the deck, false if not.
     */
    public boolean isThereAnyLeaderCard(){
        return this.leaderCardsDeck.getLeaderCards().size() > 0;
    }

    /**
     * Add one resource of a consumable kind to a specific depot.
     * @param depotIndex the index of the target depot
     * @param resourceType the type of the resource to add
     */
    public void addToDepot(int depotIndex, ResourceType resourceType) {
        warehouse.addToDepot(depotIndex, resourceType);
    }

    /**
     * Clear a depot.
     * @param index the index of the depot to clear
     */
    public void removeFromDepot(int index, ResourceType resourceType) {
        warehouse.removeFromDepot(index, resourceType);
    }

    /**
     * Add a new development card to the development cards deck.
     * @param card the new development card to add
     * @param deckIndex the target deck index
     * @throws IllegalArgumentException
     */
    public void addDevelopmentCard(DevelopmentCard card, int deckIndex) throws IllegalArgumentException{
        if(deckIndex<0 || deckIndex>2) throw new IllegalArgumentException("Invalid deck number");
        try{
            developmentCardsDecks[deckIndex].addCard(card);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Add a number of faith points to the the faith path.
     * @param points the quantity of points to add
     */
    public void addFaithPoints(int points){
        for(int i=0; i<points; i++){
            faithPath.goToNextCell();
        }
    }

    /**
     * @return the sum of cards number for each DevelopmentCards deck stored in player's board.
     */
    public int getDevelopmentCardsNumber(){
        int sum = 0;
        for(DevelopmentCardsDeck deck : developmentCardsDecks){
            sum += deck.getCardNumber();
        }
        return sum;
    }

    public int countByResourceType(ResourceType resourceType) {
        return this.warehouse.countByResourceType(resourceType) + this.strongbox.countByResourceType(resourceType);
    }

}
