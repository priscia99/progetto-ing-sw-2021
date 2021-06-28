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
import it.polimi.ingsw.server.model.player_board.storage.Strongbox;
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

    private int getInitialResourceToChoose() {
        return initialResourceToChoose;
    }

    private boolean isHasDoneMainAction() {
        return hasDoneMainAction;
    }

    private void setId(String id){
        this.id = id;
    }

    private void setUsername(String username){
        this.username = username;
    }

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
        System.out.println(username + " deve scegliere risorse");
        System.out.println("Mi stanno osservando in " + super.observers.size());
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

    public void setNickname(String nickname) {
        this.username = nickname;
    }

    public boolean finishedFaithPath() {
        return playerBoard.getFaithPath().getFaithPoints() == playerBoard.getFaithPath().getCells().length;
    }

    public int getTotalDevelopmentCards(){
        return Arrays.stream(playerBoard.getDevelopmentCardsDecks())
                .mapToInt(DevelopmentCardsDeck::getCardNumber).sum();
    }

    public void pickedLeaderCards(ArrayList<String> cardIDs) throws Exception {

        ArrayList<LeaderCard> cards = this.playerBoard.getLeaderCardsDeck().getLeaderCards()
                .stream()
                .filter(c -> !cardIDs.contains(c.getId()))
                .collect(Collectors.toCollection(ArrayList::new));
        for(LeaderCard card : cards){
            this.playerBoard.getLeaderCardsDeck().removeLeaderCardById(card.getId());
        }
        initialLeadersReady = true;

        // TODO merge in LeadersReadyMessage ore delete it
        notify(new LeaderCardsMessage(playerBoard.getLeaderCardsDeck().getLeaderCards(), username));
        notify(new LeadersReadyMessage(true));
    }

    public void pickedInitialResources(ConsumeTarget toAdd) throws Exception {
        if(toAdd.countResources() != initialResourceToChoose) throw new Exception("Invalid resource choice");
        for (ResourcePosition depotIndex : toAdd.getPositions()) {
            getPlayerBoard().addToDepot(depotIndex.ordinal(), toAdd.getToConsumeFromPosition(depotIndex).get(0).getResourceType());
        }
        initialResourcesReady = true;
        Warehouse warehouse = getPlayerBoard().getWarehouse();

        // TODO merge in ResourceReadyMessage ore delete it
        notify(new WarehouseMessage(
                warehouse.getResourceStocks()
                        .stream().map(resourceStock -> (ResourceDepot) resourceStock)
                        .collect(Collectors.toCollection(ArrayList::new)),
                username
        ));
        notify(new ResourceReadyMessage(true));
    }

    public boolean hasLeaderCards(){
        return playerBoard.isThereAnyLeaderCard();
    }

    public void addResourceToDepot(ResourceType resourceType, int depotIndex) throws Exception {
        playerBoard.getWarehouse().addToDepot(depotIndex, resourceType);
    }

    private void addToLeaderDepot(ResourceType resourceType, int index) throws Exception {
        playerBoard.addToAdditionalDepot(resourceType, index);
        notify(new LeaderCardsMessage(this.playerBoard.getLeaderCardsDeck().getLeaderCards(), username));
    }

    public void addAllResourceToDepots(ArrayList<ResourceType> types, ArrayList<ResourcePosition> depots) throws Exception {
        for(int i = 0; i< types.size(); i++){
            switch (depots.get(i)) {
                case FIRST_LEADER_DEPOT -> addToLeaderDepot(types.get(i), 0);
                case SECOND_LEADER_DEPOT -> addToLeaderDepot(types.get(i), 1);
                default -> addResourceToDepot(types.get(i), depots.get(i).ordinal());
            }
        }
        notify(new WarehouseMessage(this.playerBoard.getWarehouse().getDepots(), username));
    }

    public void addResourcesToStrongBox(ResourceStock resources) throws Exception {
        for(int i = 0; i < resources.getQuantity(); i++){
            playerBoard.getStrongbox().addResource(resources.getResourceType());
        }
        notify(new StrongboxMessage(playerBoard.getStrongbox().getResourceStocks(), username));
    }

    // count in strongbox, then count in warehouse and sum all in result
    public int countByResource(ResourceType resourceType) {
        return this.playerBoard.countByResourceType(resourceType);
    }

    // count in developmentCardsDecks where cards' color is equal to the given color
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

    // count in developmentCardsDecks where cards' level is equal to the given level
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

    public void checkPopeFavour(int position){
        boolean activated = this.getPlayerBoard().getFaithPath().checkPopeFavor(position);
        if(activated){
            notify(new FaithPathMessage(this.getPlayerBoard().getFaithPath(), username));
        }
    }

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

    public void dropLeaderCardById(String id) throws Exception {
        this.playerBoard.getLeaderCardsDeck().removeLeaderCardById(id);
        notify(new LeaderCardsMessage(this.playerBoard.getLeaderCardsDeck().getLeaderCards(), username));
    }


    public void playLeaderCardById(String cardId) {
        this.playerBoard.getLeaderCardsDeck().activateLeaderCardById(cardId);
        notify(new LeaderCardsMessage(this.getPlayerBoard().getLeaderCardsDeck().getLeaderCards(), username));
    }


    public int addFaithPoint(){
        this.playerBoard.addFaithPoints(1);
        FaithPath faithPath = this.playerBoard.getFaithPath();
        notify(new FaithPathMessage(faithPath, username));
        return this.playerBoard.getFaithPath().getFaithPoints();
    }


    public int getVictoryPoints(){
        int developmentCardsPoints = Arrays.stream(this.playerBoard.getDevelopmentCardsDecks()).mapToInt(deck-> deck.getDeck().stream().mapToInt(DevelopmentCard::getVictoryPoints).sum()).sum();
        int faithPoints = this.playerBoard.getFaithPath().getVictoryPoints();
        int leaderPoints = this.playerBoard.getLeaderCardsDeck().getLeaderCards().stream().mapToInt(leader-> leader.isActive() ? leader.getVictoryPoints() : 0).sum();
        int resourcePoints = (int) (Math.floor(this.playerBoard.getStrongbox().getResourceCount()/5.0) + Math.floor(this.playerBoard.getWarehouse().getResourceCount()/5.0));
        return developmentCardsPoints + faithPoints + leaderPoints + resourcePoints;
    }

    public int getResourceCount(){
        return this.playerBoard.getStrongbox().getResourceCount() + this.playerBoard.getWarehouse().getResourceCount();
    }

    /**
     *
     * @param index1
     * @param index2
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
     * @param index
     * @param resourceType
     */
    public void addToDepot(int index, ResourceType resourceType) throws Exception{
        Warehouse warehouse = playerBoard.getWarehouse();

        warehouse.addToDepot(index, resourceType);
        notify(new WarehouseMessage(
                warehouse.getResourceStocks()
                        .stream().map(resourceStock -> (ResourceDepot) resourceStock)
                        .collect(Collectors.toCollection(ArrayList::new)),
                username
                ));
    }



    public boolean canAddDevelopmentCard(DevelopmentCard card, int index){
        return this.playerBoard.getDevelopmentCardsDecks()[index].canAddCard(card);
    }

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

    public void addDevelopmentCard(DevelopmentCard card, int deckIndex) throws Exception {
        this.playerBoard.addDevelopmentCard(card, deckIndex);
        notify(new DevelopmentCardsMessage(card, deckIndex, username));
    }

    public ArrayList<DiscountEffect> getDiscounts(){
        return this.playerBoard.getLeaderCardsDeck()
                .getLeaderCards().stream().filter(LeaderCard::isActive)
                .filter(card->card.getEffect().getEffectType().equals(EffectType.DISCOUNT))
                .map(card->(DiscountEffect) card.getEffect())
                .collect(Collectors.toCollection(ArrayList::new));
    }

}
