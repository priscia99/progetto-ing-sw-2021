package it.polimi.ingsw.server_model.game;

import it.polimi.ingsw.server_model.card.DevelopmentCard;
import it.polimi.ingsw.server_model.card.LeaderCard;
import it.polimi.ingsw.server_model.card.color.Color;
import it.polimi.ingsw.server_model.player_board.DevelopmentCardsDeck;
import it.polimi.ingsw.server_model.player_board.PlayerBoard;
import it.polimi.ingsw.server_model.resource.ResourceStock;
import it.polimi.ingsw.server_model.resource.ResourceType;
import it.polimi.ingsw.utils.CustomLogger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Player {

    private String username;
    private final String id;
    private final PlayerBoard playerBoard;
    private boolean first;
    private int initialResourceToChoose; // FIXME
    private boolean ready;
    private boolean isCurrent;
    private boolean hasDoneMainAction;

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
        CustomLogger.getLogger().info("Player created");
    }

    public void setInitialResourceToChoose(int value){
        // FIXME
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

    // FIXME right place ????????????????????????????????????
    private boolean finishedFaithPath() {
        return playerBoard.getFaithPath().getFaithPoints() == playerBoard.getFaithPath().getCells().length;
    }

    // FIXME right place ????????????????????????????????????
    private int getTotalDevelopmentCard(){
        return Arrays.stream(playerBoard.getDevelopmentCardsDecks())
                .mapToInt(DevelopmentCardsDeck::getCardNumber).sum();
    }

    public void pickedLeaderCards(List<LeaderCard> cards){
        this.playerBoard.getLeaderCardsDeck().clear();
        this.playerBoard.addToLeaderCardsDeck(cards);
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
