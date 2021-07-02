package it.polimi.ingsw.server.model.game;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.from_server.ChooseInitialResourcesMessage;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.server.model.card.DevelopmentCard;
import it.polimi.ingsw.server.model.card.LeaderCard;
import it.polimi.ingsw.server.model.card.color.Color;
import it.polimi.ingsw.server.model.card.effect.DiscountEffect;
import it.polimi.ingsw.server.model.card.effect.EffectType;
import it.polimi.ingsw.server.model.player_board.DevelopmentCardsDeck;
import it.polimi.ingsw.server.model.player_board.PlayerBoard;
import it.polimi.ingsw.server.model.player_board.faith_path.FaithPath;
import it.polimi.ingsw.server.model.player_board.storage.Warehouse;
import it.polimi.ingsw.server.model.resource.*;
import it.polimi.ingsw.utils.CustomLogger;
import it.polimi.ingsw.network.message.from_server.*;

import java.util.*;
import java.util.stream.Collectors;

public class Player extends Observable<Message<ClientController>> {

    private String id;
    private String username;
    private boolean first;
    private PlayerBoard playerBoard;
    private int initialResourceToChoose;
    private boolean initialLeadersReady;
    private boolean initialResourcesReady;
    private boolean isCurrent;
    private boolean hasDoneMainAction;

    public boolean isInitialLeadersReady() {
        return initialLeadersReady;
    }

    public void setInitialLeadersReady(boolean initialLeadersReady) {
        this.initialLeadersReady = initialLeadersReady;
    }

    public boolean isInitialResourcesReady() {
        return initialResourcesReady;
    }

    public void setInitialResourcesReady(boolean initialResourcesReady) {
        this.initialResourcesReady = initialResourcesReady;
    }

    private void setId(String id){
        this.id = id;
    }

    private void setUsername(String username){
        this.username = username;
    }

    /**
     *
     * @return Copy of this player
     * @throws Exception
     */
    public Player getCopy() throws Exception {
        Player copy = new Player();
        copy.setId(this.id);
        copy.setUsername(this.username);
        copy.setPlayerBoard(this.playerBoard.getCopy());
        copy.setFirst(this.isFirst());
        copy.setInitialResourceToChoose(this.initialResourceToChoose);
        copy.setInitialLeadersReady(this.initialLeadersReady);
        copy.setInitialResourcesReady(this.initialResourcesReady);
        copy.setCurrent(this.isCurrent);
        copy.setHasDoneMainAction(this.hasDoneMainAction);
        return copy;
    }

    private void setPlayerBoard(PlayerBoard board){
        this.playerBoard = board;
    }

    public boolean isCurrent() {
        return isCurrent;
    }

    public void setCurrent(boolean current) {
        isCurrent = current;
    }

    public boolean hasDoneMainAction() {
        return hasDoneMainAction;
    }

    public void setHasDoneMainAction(boolean hasDoneMainAction) {
        this.hasDoneMainAction = hasDoneMainAction;
        notify(new MainActionDoneMessage(hasDoneMainAction));
    }


    public Player(String username) {
        CustomLogger.getLogger().info("Creating player");
        initialResourceToChoose = 0;
        this.username = username;
        this.id = UUID.randomUUID().toString();
        this.playerBoard = new PlayerBoard();
        setInitialLeadersReady(false);
        setInitialResourcesReady(false);
        CustomLogger.getLogger().info("Player created");
    }

    private Player(){
        this.playerBoard = new PlayerBoard();
    }

    public void setInitialResourceToChoose(int value){
        initialResourceToChoose = value;
        notify(new ChooseInitialResourcesMessage(this.username, value));
    }

    public void setInitialLeadersToChoose(ArrayList<LeaderCard> cards){
        playerBoard.getLeaderCardsDeck().addLeaders(cards);
        notify(new ChooseInitialLeadersMessage(cards, this.username));
    }

    public void hasChosenInitialResource(){
        initialResourceToChoose--;
    }

    public boolean hasToChooseInitialResource(){
        return initialResourceToChoose > 0;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean value) {
        first = value;
    }

    public String getId(){
        return id;
    }

    public String getNickname() {
        return username;
    }

    public PlayerBoard getPlayerBoard() {
        return playerBoard;
    }

    public boolean hasFinishedFaithPath() {
        return playerBoard.getFaithPath().getFaithPoints() >= playerBoard.getFaithPath().getCells().length-1;
    }

    public int getTotalDevelopmentCards(){
        return Arrays.stream(playerBoard.getDevelopmentCardsDecks())
                .mapToInt(DevelopmentCardsDeck::getCardNumber).sum();
    }

    /**
     * Update leader cards of player with the ones matching IDs in argument
     * @param cardIDs IDs of initial cards selected
     * @throws Exception
     */
    public void pickedLeaderCards(ArrayList<String> cardIDs) throws Exception {

        ArrayList<LeaderCard> cards = this.playerBoard.getLeaderCardsDeck().getLeaderCards()
                .stream()
                .filter(c -> !cardIDs.contains(c.getId()))
                .collect(Collectors.toCollection(ArrayList::new));
        for(LeaderCard card : cards){
            this.playerBoard.getLeaderCardsDeck().removeLeaderCardById(card.getId());
        }
        initialLeadersReady = true;
        notify(new LeaderCardsMessage(playerBoard.getLeaderCardsDeck().getLeaderCards(), username));
    }

    /**
     * Add resources selected when game is starting
     * @param toAdd Resources and position selected
     * @throws Exception
     */
    public void pickedInitialResources(ConsumeTarget toAdd) throws Exception {
        if(toAdd.countResources() != initialResourceToChoose) throw new Exception("Invalid resource choice");
        for (ResourcePosition depotIndex : toAdd.getPositions()) {
            getPlayerBoard().addToDepot(depotIndex.ordinal(), toAdd.getToConsumeFromPosition(depotIndex).get(0).getResourceType());
        }
        initialResourcesReady = true;
        Warehouse warehouse = getPlayerBoard().getWarehouse();

        notify(new WarehouseMessage(
                warehouse.getResourceStocks()
                        .stream().map(resourceStock -> (ResourceDepot) resourceStock)
                        .collect(Collectors.toCollection(ArrayList::new)),
                username
        ));
    }

    public boolean hasLeaderCards(){
        return playerBoard.isThereAnyLeaderCard();
    }

    /**
     * Add resource in depot selected
     * @param resourceType Resource to insert
     * @param depotIndex Depot selected for insertion
     * @throws Exception
     */
    public void addResourceToDepot(ResourceType resourceType, int depotIndex) throws Exception {
        playerBoard.getWarehouse().addToDepot(depotIndex, resourceType);
    }

    /**
     * Add resource in leader depot
     * @param resourceType Resource to insert
     * @param index Leader depot selected for insertion
     * @throws Exception
     */
    private void addToLeaderDepot(ResourceType resourceType, int index) throws Exception {
        playerBoard.addToAdditionalDepot(resourceType, index);
        notify(new LeaderCardsMessage(this.playerBoard.getLeaderCardsDeck().getLeaderCards(), username));
    }

    /**
     * Add multiple resources to multiple depots
     * @param types Resources to insert
     * @param depots Depot positions
     * @throws Exception
     */
    public void addAllResourceToDepots(ArrayList<ResourceType> types, ArrayList<ResourcePosition> depots) throws Exception {
        for(int i = 0; i< types.size(); i++){
            switch (depots.get(i)) {
                case FIRST_LEADER_DEPOT -> addToLeaderDepot(types.get(i), 0);
                case SECOND_LEADER_DEPOT -> addToLeaderDepot(types.get(i), 1);
                default -> addResourceToDepot(types.get(i), depots.get(i).ordinal());
            }
        }
        notify(new WarehouseMessage(this.playerBoard.getWarehouse().getDepots(), username));
        notify(new UpdateVictoryPointsMessage(getVictoryPoints(), username));
    }

    /**
     * Add resources to strongbox
     * @param resources Resources to insert
     * @throws Exception
     */
    public void addResourcesToStrongBox(ResourceStock resources) throws Exception {
        for(int i = 0; i < resources.getQuantity(); i++){
            playerBoard.getStrongbox().addResource(resources.getResourceType());
        }
        notify(new StrongboxMessage(playerBoard.getStrongbox().getResourceStocks(), username));
        notify(new UpdateVictoryPointsMessage(getVictoryPoints(), username));
    }

    /**
     *
     * @param resourceType Resources to count
     * @return Quantity of resource owned by player with selected resource type
     */
    public int countByResource(ResourceType resourceType) {
        return this.playerBoard.countByResourceType(resourceType);
    }

    /**
     *
     * @param color Color of development card to count
     * @return Quantity of development cards owned by player with selected color
     */
    public int countByColor(Color color) {
        int result = 0;

        for(DevelopmentCardsDeck deck : this.playerBoard.getDevelopmentCardsDecks()) {
            result += deck.getDeck()
                    .stream()
                    .filter(developmentCard -> developmentCard.getColor().equals(color))
                    .count();
        }

        return result;
    }


    /**
     *
     * @param level Level of development card to count
     * @return Quantity of development card owned by player with selected level
     */
    public int countByLevel(int level) {
        int result = 0;

        for(DevelopmentCardsDeck deck : this.playerBoard.getDevelopmentCardsDecks()) {
            result += deck.getDeck()
                    .stream()
                    .filter(developmentCard -> developmentCard.getLevel()==level)
                    .count();
        }

        return result;
    }

    /**
     * Trigger pope favour cell if present in position
     * @param position
     */
    public void checkPopeFavour(int position){
        boolean activated = this.getPlayerBoard().getFaithPath().checkPopeFavor(position);
        if(activated){
            notify(new FaithPathMessage(this.getPlayerBoard().getFaithPath(), username));
        }
    }

    /**
     *
     * @param level Level of development cards to consider
     * @return Colors of development cards with level selected
     */
    public ArrayList<Color> colorByLevel(int level) {
        if (level < 0 || level > 3) {
            throw new IllegalArgumentException("level must be in [0;3]");
        }

        ArrayList<Color> colors = new ArrayList<Color>();

        for(DevelopmentCardsDeck deck: this.playerBoard.getDevelopmentCardsDecks()) {
            ArrayList<Color> tempColors = deck.getDeck()
                    .stream()
                    .filter(developmentCard -> developmentCard.getLevel() == level)
                    .map(DevelopmentCard::getColor)
                    .distinct()
                    .collect(Collectors.toCollection(ArrayList::new));

            colors.addAll(tempColors);
        }

        return colors;
    }

    /**
     * Remove leader card with matching ID
     * @param id ID of leader card to remove
     * @throws Exception
     */
    public void dropLeaderCardById(String id) throws Exception {
        this.playerBoard.getLeaderCardsDeck().removeLeaderCardById(id);
        notify(new LeaderCardsMessage(this.playerBoard.getLeaderCardsDeck().getLeaderCards(), username));
    }

    /**
     * Activate leader card with matching ID
     * @param cardId ID of leader card to activate
     */
    public void activateLeaderCardById(String cardId) {
        this.playerBoard.getLeaderCardsDeck().activateLeaderCardById(cardId);
        notify(new LeaderCardsMessage(this.getPlayerBoard().getLeaderCardsDeck().getLeaderCards(), username));
        notify(new UpdateVictoryPointsMessage(getVictoryPoints(), username));
    }


    /**
     * Add faith point to player
     * @return position of player in faith path
     */
    public int addFaithPoint(){
        this.playerBoard.addFaithPoints(1);
        FaithPath faithPath = this.playerBoard.getFaithPath();
        notify(new FaithPathMessage(faithPath, username));
        return this.playerBoard.getFaithPath().getFaithPoints();
    }

    /**
     *
     * @return Victory points of player
     */
    public int getVictoryPoints(){
        int developmentCardsPoints = Arrays.stream(this.playerBoard.getDevelopmentCardsDecks()).mapToInt(deck-> deck.getDeck().stream().mapToInt(DevelopmentCard::getVictoryPoints).sum()).sum();
        int faithPoints = this.playerBoard.getFaithPath().getVictoryPoints();
        int leaderPoints = this.playerBoard.getLeaderCardsDeck().getLeaderCards().stream().mapToInt(leader-> leader.isActive() ? leader.getVictoryPoints() : 0).sum();
        int resourcePoints = (int) (Math.floor(this.getResourceCount()/5.0));
        return developmentCardsPoints + faithPoints + leaderPoints + resourcePoints;
    }

    /**
     *
     * @return Quantity of resources owned by player
     */
    public int getResourceCount(){
        return
                this.playerBoard.getStrongbox().countResources() +
                this.playerBoard.getWarehouse().countResources() +
                this.playerBoard.getLeaderCardsDeck().countResources();
    }

    /**
     * Swap depots of player
     * @param index1 Index of firsts depot to swap
     * @param index2 Index of second depot to swap
     */
    public void swapDepots(int index1, int index2) throws Exception {
        playerBoard.getWarehouse().swap(index1, index2);
        ArrayList<ResourceStock> resourceStocks = playerBoard.getWarehouse().getResourceStocks();

        notify(new WarehouseMessage(
                resourceStocks
                        .stream()
                        .map(resourceStock -> (ResourceDepot) resourceStock)
                        .collect(Collectors.toCollection(ArrayList::new)),
                username
        ));
    }


    /**
     *
     * @param card Card to add
     * @param index Depot index into which player want to insert card
     * @return Check if player can add selected card in selected depot
     */
    public boolean canAddDevelopmentCard(DevelopmentCard card, int index){
        return this.playerBoard.getDevelopmentCardsDecks()[index].canAddCard(card);
    }

    /**
     *
     * @param toConsume Resources with positions
     * @return Check if player can consume selected resources from selected positions
     */
    public boolean canConsume(ConsumeTarget toConsume){
        return !toConsume.getPositions().stream().map(position -> {
            if(position.equals(ResourcePosition.STRONG_BOX)){
                return !toConsume.getToConsumeFromStrongBox().stream()
                        .map(stock->this.playerBoard.getStrongbox().canConsume(stock))
                        .collect(Collectors.toList()).contains(false);
            } else {
                return switch (position) {
                    case FIRST_LEADER_DEPOT -> this.playerBoard
                            .canConsumeFromLeaderDepot(0, toConsume.getToConsumeFromDepot(position));
                    case SECOND_LEADER_DEPOT -> this.playerBoard
                            .canConsumeFromLeaderDepot(1, toConsume.getToConsumeFromDepot(position));
                    default -> this.playerBoard.getWarehouse()
                            .canConsumeFromDepot(position, toConsume.getToConsumeFromDepot(position));
                };

            }
        }).collect(Collectors.toList()).contains(false);
    }

    /**
     * Consume selected resources from the selected positions
     * @param toConsume Resources and positions selected
     * @throws Exception
     */
    public void consumeResources(ConsumeTarget toConsume) throws Exception {
        for(ResourcePosition position : toConsume.getPositions()){
            if(position == ResourcePosition.STRONG_BOX){
                for(ResourceStock stock : toConsume.getToConsumeFromStrongBox()){
                    this.playerBoard.getStrongbox().consume(stock);
                }
            } else {
                switch (position) {
                    case FIRST_LEADER_DEPOT -> this.playerBoard.consumeFromLeaderDepot(0, toConsume.getToConsumeFromDepot(position));
                    case SECOND_LEADER_DEPOT -> this.playerBoard.consumeFromLeaderDepot(1, toConsume.getToConsumeFromDepot(position));
                    default -> this.playerBoard.getWarehouse().consume(toConsume.getToConsumeFromDepot(position));
                }
            }
        }
        notify(new LeaderCardsMessage(this.playerBoard.getLeaderCardsDeck().getLeaderCards(), username));
        notify(new StrongboxMessage(this.playerBoard.getStrongbox().getResourceStocks(), username));
        notify(new WarehouseMessage(
                this.playerBoard.getWarehouse().getResourceStocks()
                        .stream().map(resourceStock -> (ResourceDepot) resourceStock)
                        .collect(Collectors.toCollection(ArrayList::new)),
                username
        ));
    }

    /**
     * Add development card to player
     * @param card Card to add
     * @param deckIndex Deck into which card has to be added
     * @throws Exception
     */
    public void addDevelopmentCard(DevelopmentCard card, int deckIndex) throws Exception {
        this.playerBoard.addDevelopmentCard(card, deckIndex);
        notify(new DevelopmentCardsMessage(card, deckIndex, username));
        notify(new UpdateVictoryPointsMessage(getVictoryPoints(), username));
    }

    /**
     *
     * @return List of discounts available by player
     */
    public ArrayList<DiscountEffect> getDiscounts(){
        return this.playerBoard.getLeaderCardsDeck()
                .getLeaderCards().stream().filter(LeaderCard::isActive)
                .filter(card->card.getEffect().getEffectType().equals(EffectType.DISCOUNT))
                .map(card->(DiscountEffect) card.getEffect())
                .collect(Collectors.toCollection(ArrayList::new));
    }

}
