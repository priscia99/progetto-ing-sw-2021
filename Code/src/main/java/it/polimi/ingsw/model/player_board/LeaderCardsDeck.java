package it.polimi.ingsw.model.player_board;

import it.polimi.ingsw.data.LeaderCardsBuilder;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.card.effect.DiscountEffect;
import it.polimi.ingsw.model.card.effect.EffectType;
import it.polimi.ingsw.model.resource.ResourceType;
import it.polimi.ingsw.utils.CustomLogger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LeaderCardsDeck {

    private final ArrayList<LeaderCard> leaderCards;

    // TODO: Remove this function and call builder in setupLeaderCards()
    static public LeaderCardsDeck getStartingDeck(){
        return new LeaderCardsDeck((ArrayList<LeaderCard>)LeaderCardsBuilder.getDeck());
    }

    public LeaderCardsDeck(ArrayList<LeaderCard> leaderCards) {
        this.leaderCards = leaderCards;
    }

    public LeaderCardsDeck(){
        this.leaderCards = new ArrayList<>();
    }

    public void shuffle(){
        Collections.shuffle(this.leaderCards);
    }

    public LeaderCard pop(){
        //TODO: add error if deck is empty
        return this.leaderCards.remove(0);
    }


    public ArrayList<LeaderCard> getLeaderCards() {
        return leaderCards;
    }

    public void addLeader(LeaderCard leaderCard) {
        // TODO: implement addLeader();
    }

    public void removeLeaderCard(LeaderCard leaderCard) {
        // TODO: implement removeLeaderCard();
    }
}
