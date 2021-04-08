package it.polimi.ingsw.model.player_board;

import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.card.effect.ProductionEffect;
import it.polimi.ingsw.model.player_board.faith_path.FaithPath;
import it.polimi.ingsw.model.player_board.storage.Strongbox;
import it.polimi.ingsw.model.player_board.storage.Warehouse;
import it.polimi.ingsw.model.resource.ResourceType;

import java.util.List;
import java.util.Stack;

public class PlayerBoard {

    private  FaithPath faithPath;
    private  ProductionEffect basicProduction;
    private  DevelopmentCardsDeck[] developmentCardsDecks;
    private  LeaderCardsDeck leaderCardsDeck;
    private  Warehouse warehouse;
    private  Strongbox strongbox;

    public PlayerBoard() {
        setupFaithPath();
        setupBasicProduction();
        setupDevelopmentCardDeck();
        setupLeaderCards();
        setupWarehouse();
        setupStrongBox();
    }

    private void setupStrongBox() {
        this.strongbox = new Strongbox();
    }

    private void setupWarehouse() {
        this.warehouse = new Warehouse();
    }

    private void setupLeaderCards() {
        this.leaderCardsDeck = new LeaderCardsDeck();
    }

    private void setupDevelopmentCardDeck() {
        this.developmentCardsDecks = new DevelopmentCardsDeck[3];
    }

    private void setupBasicProduction() {
        this.basicProduction = ProductionEffect.getBasicProduction();
    }

    private void setupFaithPath() {
        this.faithPath = FaithPath.getStandardFaithPath();
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

    public void addToLeaderCardsDeck(List<LeaderCard> leadersToAdd){
        for(LeaderCard card : leadersToAdd){
            this.leaderCardsDeck.addLeader(card);
        }
    }

    public void removeFromLeaderCardsDeck(LeaderCard card){
        this.leaderCardsDeck.removeLeaderCard(card);
    }

    public boolean leaderCardsArePresent(){
        return this.leaderCardsDeck.getLeaderCards().size() > 0;
    }

    public void addToDepot(int depot, ResourceType resourceType) {
        warehouse.addToDepot(depot, resourceType);
    }

    public void removeFromDepot(int depot) {
        warehouse.removeFromDepot(depot);
    }

    public void addDevelopmentCard(DevelopmentCard card, int deck) throws IllegalArgumentException{
        // Controllo dell'indice deck ricevuto in ingresso
        if(deck<0 || deck>2) throw new IllegalArgumentException("Invalid deck number");
        try{
            // I controlli dello specifico deck sono rilegati alla funzione interna di DevelopmentCardDeck
            developmentCardsDecks[deck].addCard(card);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void addFaithPoints(int points){
        // TODO check victory
        for(int i=0; i<points; i++){
            faithPath.goToNextCell();
        }
    }

}
