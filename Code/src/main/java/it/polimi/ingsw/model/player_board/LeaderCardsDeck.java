package it.polimi.ingsw.model.player_board;

import it.polimi.ingsw.data.LeaderCardsBuilder;
import it.polimi.ingsw.exceptions.EmptyDeckException;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.card.effect.DiscountEffect;
import it.polimi.ingsw.model.card.effect.EffectType;
import it.polimi.ingsw.model.resource.ResourceType;
import it.polimi.ingsw.utils.CustomLogger;

import java.util.*;

public class LeaderCardsDeck {

    private final ArrayList<LeaderCard> leaderCards;

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
        if(leaderCards.isEmpty()) throw new EmptyDeckException("This player's leader cards deck is empty.");
        return this.leaderCards.remove(0);
    }


    public ArrayList<LeaderCard> getLeaderCards() {
        return leaderCards;
    }

    public void addLeader(LeaderCard leaderCard) {
        leaderCards.add(leaderCard);
    }

    public void removeLeaderCard(LeaderCard leaderCard) {
        if(leaderCards.isEmpty()) throw new EmptyDeckException("This player's leader cards deck is empty.");
        if(!leaderCards.remove(leaderCard))
            throw new IllegalArgumentException("LeaderCard not present this player's deck");
    }
}
