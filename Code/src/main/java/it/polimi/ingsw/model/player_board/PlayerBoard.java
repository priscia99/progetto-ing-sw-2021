package it.polimi.ingsw.model.player_board;

import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.card.effect.ProductionEffect;
import it.polimi.ingsw.model.locally_copiable.LocallyCopyable;
import it.polimi.ingsw.model.player_board.faith_path.FaithPath;
import it.polimi.ingsw.model.player_board.storage.Strongbox;
import it.polimi.ingsw.model.player_board.storage.Warehouse;
import it.polimi.ingsw.model.resource.ResourceType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class PlayerBoard implements LocallyCopyable<PlayerBoard.PlayerBoardLocalCopy> {

    private final FaithPath faithPath;
    private final ProductionEffect basicProduction;
    private final DevelopmentCardsDeck[] developmentCardsDecks;
    private final LeaderCardsDeck leaderCardsDeck;
    private final Warehouse warehouse;
    private final Strongbox strongbox;

        public static class PlayerBoardLocalCopy {

        private final int faithPoints;
        private final ArrayList<DevelopmentCardsDeck.DevelopmentCardsDeckLocalCopy> developmentCardsDeckLocalCopies;
        private final LeaderCardsDeck.LeaderCardsDeckLocalCopy leaderCardsDeckLocalCopy;
        private final Warehouse.WarehouseLocalCopy warehouseLocalCopy;
        private final Strongbox.StrongboxLocalCopy strongboxLocalCopy;

        public PlayerBoardLocalCopy(int faithPoints,
                                    ArrayList<DevelopmentCardsDeck.DevelopmentCardsDeckLocalCopy> developmentCardsDeckLocalCopies,
                                    LeaderCardsDeck.LeaderCardsDeckLocalCopy leaderCardsDeckLocalCopy,
                                    Warehouse.WarehouseLocalCopy warehouseLocalCopy,
                                    Strongbox.StrongboxLocalCopy strongboxLocalCopy)
        {
            this.faithPoints = faithPoints;
            this.developmentCardsDeckLocalCopies = developmentCardsDeckLocalCopies;
            this.leaderCardsDeckLocalCopy = leaderCardsDeckLocalCopy;
            this.warehouseLocalCopy = warehouseLocalCopy;
            this.strongboxLocalCopy = strongboxLocalCopy;
        }

            public int getFaithPoints() {
                return faithPoints;
            }

            // TODO implement facade methods

    }

    @Override
    public PlayerBoardLocalCopy getLocalCopy() {
        return new PlayerBoardLocalCopy(
                this.faithPath.getFaithPoints(),
                Arrays.stream(this.developmentCardsDecks)
                        .map(DevelopmentCardsDeck::getLocalCopy)
                        .collect(Collectors.toCollection(ArrayList::new)),
                this.leaderCardsDeck.getLocalCopy(),
                this.warehouse.getLocalCopy(),
                this.strongbox.getLocalCopy()
        );
    }

    public PlayerBoard() {
        this.strongbox = new Strongbox();
        this.warehouse = new Warehouse();
        this.leaderCardsDeck = new LeaderCardsDeck();
        this.developmentCardsDecks = new DevelopmentCardsDeck[3];
        this.basicProduction = ProductionEffect.getBasicProduction();
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

    public Warehouse getWarehouse() { return warehouse; }

    public Strongbox getStrongbox() {
        return strongbox;
    }

    public void addToLeaderCardsDeck(List<LeaderCard> leadersToAdd){
        for(LeaderCard card : leadersToAdd){
            this.leaderCardsDeck.addLeader(card);
        }
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

}
