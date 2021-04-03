package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.card.Card;
import it.polimi.ingsw.model.card.color.Color;
import it.polimi.ingsw.model.player_board.LeaderCardsDeck;
import it.polimi.ingsw.model.resource.ResourcePile;
import it.polimi.ingsw.model.resource.ResourceType;

import java.util.List;

public class Player {

    private String nickname;
    private Game game;

    public Player(String nickname, Game game) {
        this.nickname = nickname;
        this.game = game;
    }

    public String getNickname() {
        return nickname;
    }

    public Game getGame() {
        return game;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void pickCard(List<Card> cards) {

    }

    public void addResource(ResourcePile resourcePile) {

    }

    public void pickAction() {

    }

    public int countByResource(ResourceType resourceType) {
        return 0;
    }

    public int countByColor(Color color) {
        return 0;
    }

    public int countByLevel(int level) {
        return 0;
    }

    public void colorByLevel(int level) {

    }
}
