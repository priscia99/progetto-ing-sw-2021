package it.polimi.ingsw.server.model.player_board;

import it.polimi.ingsw.exceptions.GameException;
import it.polimi.ingsw.server.model.card.DevelopmentCard;
import it.polimi.ingsw.server.model.card.LeaderCard;
import it.polimi.ingsw.server.model.card.effect.DepotEffect;
import it.polimi.ingsw.server.model.card.effect.DiscountEffect;
import it.polimi.ingsw.server.model.card.effect.EffectType;
import it.polimi.ingsw.server.model.card.effect.ProductionEffect;
import it.polimi.ingsw.server.model.player_board.faith_path.FaithPath;
import it.polimi.ingsw.server.model.player_board.storage.Strongbox;
import it.polimi.ingsw.server.model.player_board.storage.Warehouse;
import it.polimi.ingsw.server.model.resource.ResourceDepot;
import it.polimi.ingsw.server.model.resource.ResourceStock;
import it.polimi.ingsw.server.model.resource.ResourceType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class that models the player board. It instantiate a faith path, storages and slots for cards. It take count of the
 * player statistics such as victory points.
 */
public class PlayerBoard {

    private  FaithPath faithPath;
    private  ProductionEffect basicProduction;
    private  DevelopmentCardsDeck[] developmentCardsDecks;
    private  LeaderCardsDeck leaderCardsDeck;
    private  Warehouse warehouse;
    private  Strongbox strongbox;

    /**
     * Create an empty PlayerBoard object.
     */
    public PlayerBoard() {
        this.strongbox = new Strongbox();
        this.warehouse = new Warehouse();
        this.leaderCardsDeck = new LeaderCardsDeck();
        this.developmentCardsDecks = createDevelopmentCardsDeck();
        this.basicProduction = ProductionEffect.getBasicProduction();
        this.faithPath = FaithPath.getStandardFaithPath();
    }

    private DevelopmentCardsDeck[] createDevelopmentCardsDeck(){
        DevelopmentCardsDeck[] developmentCardsDeck = new DevelopmentCardsDeck[3];
        developmentCardsDeck[0] = new DevelopmentCardsDeck(new ArrayList<>());
        developmentCardsDeck[1] = new DevelopmentCardsDeck(new ArrayList<>());
        developmentCardsDeck[2] = new DevelopmentCardsDeck(new ArrayList<>());
        return developmentCardsDeck;
    }

    public PlayerBoard getCopy() throws GameException {
        PlayerBoard copy = new PlayerBoard();
        copy.setFaithPath(this.getFaithPath().getCopy());
        DevelopmentCardsDeck[] decksCopy = new DevelopmentCardsDeck[3];
        for(int i = 0; i < decksCopy.length ; i++) decksCopy[i] = this.developmentCardsDecks[i].getCopy();
        copy.setDevelopmentCardsDecks(decksCopy);
        copy.setLeaderCardsDeck(this.getLeaderCardsDeck().getCopy());
        copy.setWarehouse(this.getWarehouse().getCopy());
        copy.setStrongbox(this.getStrongbox().getCopy());
        return copy;
    }

    /**
     *
     * @return the faith path object.
     */
    public FaithPath getFaithPath() {
        return faithPath;
    }

    private void setFaithPath(FaithPath faithPath){this.faithPath = faithPath;}
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

    private void setDevelopmentCardsDecks(DevelopmentCardsDeck[] decks){
        this.developmentCardsDecks = decks;
    }

    private void setLeaderCardsDeck(LeaderCardsDeck deck){
        this.leaderCardsDeck = deck;
    }

    private void setWarehouse(Warehouse warehouse){this.warehouse = warehouse;}

    private void setStrongbox(Strongbox strongbox){this.strongbox = strongbox;}
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
    public void addToDepot(int depotIndex, ResourceType resourceType) throws GameException {
        warehouse.addToDepot(depotIndex, resourceType);
    }

    /**
     * Add a new development card to the development cards deck.
     * @param card the new development card to add
     * @param deckIndex the target deck index
     * @throws IllegalArgumentException
     */
    public void addDevelopmentCard(DevelopmentCard card, int deckIndex) throws GameException {
        if(deckIndex<0 || deckIndex>2) throw new GameException("Invalid deck number");
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

    public ArrayList<ResourceDepot> getAdditionalDepots(){
        ArrayList<DepotEffect> effects = this.leaderCardsDeck.getActiveEffects(EffectType.DEPOT);
        return effects.stream().map(DepotEffect::getDepot).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<ResourceDepot> getAdditionalDepotsCopy(){
        ArrayList<DepotEffect> effects = this.leaderCardsDeck.getActiveEffects(EffectType.DEPOT);
        ArrayList<ResourceDepot> depotCopies = new ArrayList<>();
        for(DepotEffect effect : effects){
            depotCopies.add(new ResourceDepot(
                    effect.getDepot().getResourceType(),
                    effect.getDepot().getQuantity(),
                    effect.getDepot().getCapacity()));
        }
        return depotCopies;
    }

    public void addToAdditionalDepot(ResourceType type, int index) throws GameException {
        this.getAdditionalDepots().get(index).incrementResource(type);
    }

    public boolean canConsumeFromLeaderDepot(int index, ResourceStock stock){
        boolean isCorrectResource = this.getAdditionalDepots().get(index).getResourceType() == stock.getResourceType();
        boolean areEnoughResources = this.getAdditionalDepots().get(index).getQuantity() >= stock.getQuantity();
        return isCorrectResource && areEnoughResources;
    }

    public void consumeFromLeaderDepot(int index, ResourceStock toConsume) throws GameException {
        ResourceDepot consumed = this.getAdditionalDepots().get(index);
        for(int i = 0; i<toConsume.getQuantity(); i++) consumed.decrementResource(toConsume.getResourceType());
    }


}
