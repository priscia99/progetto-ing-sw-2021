package v2.server.model.game;

import it.polimi.ingsw.exceptions.InvalidActionException;
import it.polimi.ingsw.utils.CustomLogger;
import v2.server.model.card.DevelopmentCard;
import v2.server.model.card.LeaderCard;
import v2.server.model.card.color.Color;
import v2.server.model.player_board.DevelopmentCardsDeck;
import v2.server.model.player_board.PlayerBoard;
import v2.server.model.resource.ResourcePosition;
import v2.server.model.resource.ResourceStock;
import v2.server.model.resource.ResourceType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;
import java.util.stream.Collectors;

public class Player {

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

    public void pickedLeaderCards(ArrayList<LeaderCard> cards){
        this.playerBoard.getLeaderCardsDeck().clear();
        this.playerBoard.addToLeaderCardsDeck(cards);
        setInitialLeadersReady(true);
    }

    public void pickedInitialResources(HashMap<ResourcePosition, ResourceType> toAdd){
        if(toAdd.values().size() != initialResourceToChoose) throw new InvalidActionException();
        for (ResourcePosition depotIndex : toAdd.keySet()) {
            getPlayerBoard().getWarehouse().addToDepot(depotIndex.ordinal(), toAdd.get(depotIndex));
        }
        setInitialResourcesReady(true);
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
