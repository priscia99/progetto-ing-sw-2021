package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.card.Card;
import it.polimi.ingsw.model.card.color.Color;
import it.polimi.ingsw.model.player_board.DevelopmentCardsDeck;
import it.polimi.ingsw.model.player_board.LeaderCardsDeck;
import it.polimi.ingsw.model.player_board.PlayerBoard;
import it.polimi.ingsw.model.player_board.storage.Depot;
import it.polimi.ingsw.model.resource.ResourcePile;
import it.polimi.ingsw.model.resource.ResourceType;
import it.polimi.ingsw.utils.CustomLogger;

import java.util.List;

public class Player {

    private String nickname;
    private final PlayerBoard playerBoard;

    public Player(String nickname) {
        CustomLogger.getLogger().info("Creating player");
        this.nickname = nickname;
        this.playerBoard = new PlayerBoard();
        CustomLogger.getLogger().info("Player created");
    }

    public String getNickname() {
        return nickname;
    }


    public PlayerBoard playerBoard() {
        return playerBoard;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    public void pickCard(List<Card> cards) {

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

        return result;
    }

    // count in developmentcardsDecks where cards' color is equal to the given color
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

    public void colorByLevel(int level) {

    }
}
