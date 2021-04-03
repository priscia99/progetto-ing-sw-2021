package it.polimi.ingsw.model.player_board;

import it.polimi.ingsw.model.card.LeaderCard;

import java.util.List;

public class LeaderCardsDeck {

    private List<LeaderCard> leaderCards;

    public LeaderCardsDeck(List<LeaderCard> leaderCards) {
        this.leaderCards = leaderCards;
    }

    public List<LeaderCard> getLeaderCards() {
        return leaderCards;
    }

    public void addLeader(LeaderCard leaderCard) {

    }

    public void removeLeaderCard(LeaderCard leaderCard) {

    }
}
