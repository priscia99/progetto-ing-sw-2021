package it.polimi.ingsw.model;

import java.util.List;

public class LeaderCardsDeck {

    private final List<LeaderCard> leaderCards;

    public LeaderCardsDeck(List<LeaderCard> leaderCards) {
        this.leaderCards = leaderCards;
    }

    public List<LeaderCard> getLeaderCards() {
        return leaderCards;
    }

    public void addLeaderCard(LeaderCard card) {}

    public void removeLeaderCard(LeaderCard card) {}
}
