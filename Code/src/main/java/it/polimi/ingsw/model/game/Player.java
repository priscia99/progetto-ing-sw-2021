package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.card.Card;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.card.color.Color;
import it.polimi.ingsw.model.card.effect.DepotEffect;
import it.polimi.ingsw.model.card.effect.EffectType;
import it.polimi.ingsw.model.player_board.DevelopmentCardsDeck;
import it.polimi.ingsw.model.player_board.LeaderCardsDeck;
import it.polimi.ingsw.model.player_board.PlayerBoard;
import it.polimi.ingsw.model.player_board.storage.Depot;
import it.polimi.ingsw.model.resource.ResourcePile;
import it.polimi.ingsw.model.resource.ResourceType;
import it.polimi.ingsw.utils.CustomLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Player {

    private String nickname;
    private final String id;
    private final PlayerBoard playerBoard;
    private boolean isFirst;
    private List<? extends Card> cardsToChoose;

    public Player(String nickname) {
        CustomLogger.getLogger().info("Creating player");
        this.nickname = nickname;
        this.id = UUID.randomUUID().toString();
        this.playerBoard = new PlayerBoard();
        CustomLogger.getLogger().info("Player created");
    }

    public void receiveCardsToChoose(List<? extends Card> cards){
        this.cardsToChoose = cards;
    }

    public boolean getIsFirst() { return isFirst;}
    public void setIsFirst(boolean value) { isFirst = value;}

    public String getId(){ return id;}

    public String getNickname() {
        return nickname;
    }


    public PlayerBoard getPlayerBoard() {
        return playerBoard;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    public void pickedLeaderCards(List<LeaderCard> cards){
        this.cardsToChoose.clear();
        this.playerBoard.addToLeaderCardsDeck(cards);
    }

    public boolean hasLeaderCards(){
        return playerBoard.leaderCardsArePresent();
    }

    public void addResource(ResourcePile resourcePile) {

    }

    public void pickAction() {

    }

    // count in strongbox, then count in warehouse and sum all in result
    public int countByResource(ResourceType resourceType) {
        int result = 0;

        result += this.playerBoard.getStrongbox().getConsumableResources()
                .stream()
                .filter(consumableResource -> consumableResource.getResourceType().equals(resourceType))
                .count();

        for (Depot depot : this.playerBoard.getWarehouse().getDepots()) {
            if (depot.getResourceType().equals(resourceType)) {
                result += depot.getConsumableResources().size();
            }
        }

        result += this.playerBoard.getLeaderCardsDeck().getLeaderCards()
                .stream()
                .filter(LeaderCard::isActive)
                .filter(leaderCard -> leaderCard.getEffect().getEffectType().equals(EffectType.DEPOT))
                .map(leaderCard -> {
                    int tempCount = 0;
                    if (leaderCard.getEffect() instanceof DepotEffect) {
                        DepotEffect effect = (DepotEffect) leaderCard.getEffect();
                        Depot depot = effect.getDepot();
                        tempCount = depot.getConsumableResources().size();
                    }
                    else {
                        throw new IllegalArgumentException("leaderCard.getEffect() is not instance of DepotEffect");
                    }
                    return tempCount;
                })
                .mapToInt(Integer::intValue)
                .sum();

        return result;
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

        return 0;
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
                    .collect(Collectors.toCollection(ArrayList::new));

            colors.addAll(tempColors);
        }

        return colors;
    }

    public void addFaithPoints(int count){
        this.playerBoard.addFaithPoints(count);
    }
}
