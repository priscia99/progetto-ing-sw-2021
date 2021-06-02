package it.polimi.ingsw.server.model.game;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.exceptions.InvalidActionException;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.from_server.ChooseInitialResourcesMessage;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.server.model.card.DevelopmentCard;
import it.polimi.ingsw.server.model.card.LeaderCard;
import it.polimi.ingsw.server.model.card.color.Color;
import it.polimi.ingsw.server.model.player_board.DevelopmentCardsDeck;
import it.polimi.ingsw.server.model.player_board.PlayerBoard;
import it.polimi.ingsw.server.model.resource.ResourcePosition;
import it.polimi.ingsw.server.model.resource.ResourceStock;
import it.polimi.ingsw.server.model.resource.ResourceType;
import it.polimi.ingsw.utils.CustomLogger;
import it.polimi.ingsw.network.message.from_server.*;
import it.polimi.ingsw.network.message.from_client.*;

import java.util.*;
import java.util.stream.Collectors;

public class Player extends Observable<Message<ClientController>> {

    private String username;


    private String id;
    private PlayerBoard playerBoard;
    private boolean first;
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

    public Player getCopy(){
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

    public void pickedLeaderCards(ArrayList<String> cardIDs){
        ArrayList<LeaderCard> cards = new ArrayList<>(
                this.playerBoard.getLeaderCardsDeck().getLeaderCards()
                .stream().filter(c->cardIDs.contains(c.getId())).collect(Collectors.toList())
        );
        this.playerBoard.getLeaderCardsDeck().clear();
        this.playerBoard.addToLeaderCardsDeck(cards);
        initialLeadersReady = true;
        notify(new LeadersReadyMessage(true));
    }

    public void pickedInitialResources(HashMap<ResourcePosition, ResourceType> toAdd){
        if(toAdd.values().size() != initialResourceToChoose) throw new InvalidActionException();
        for (ResourcePosition depotIndex : toAdd.keySet()) {
            getPlayerBoard().getWarehouse().addToDepot(depotIndex.ordinal(), toAdd.get(depotIndex));
        }
        initialLeadersReady = true;
        notify(new ResourceReadyMessage(true));
    }

    public boolean hasLeaderCards(){
        return playerBoard.isThereAnyLeaderCard();
    }

    public void addResourceToDepot(ResourceType resourceType, int depotIndex){
        playerBoard.getWarehouse().addToDepot(depotIndex, resourceType);
    }

    public void addResourcesToStrongBox(ResourceStock resources){
        // FIXME remove loops and add all resources at once
        for(int i = 0; i < resources.getQuantity(); i++)
            playerBoard.getStrongbox().addResource(resources.getResourceType());
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

    public void addFaithPoints(int count){
        this.playerBoard.addFaithPoints(count);
    }
}