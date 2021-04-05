package it.polimi.ingsw.model.player_board;

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

    static public LeaderCardsDeck getStartingDeck(){
        final LeaderCard[] defaultDeckArray = {
                //TODO: complete default array deck with correct leader cards data
                new LeaderCard(false, 3,new DiscountEffect(ResourceType.COIN)),
                new LeaderCard(false, 3,new DiscountEffect(ResourceType.COIN)),
                new LeaderCard(false, 3,new DiscountEffect(ResourceType.COIN)),
                new LeaderCard(false, 3,new DiscountEffect(ResourceType.COIN)),
                new LeaderCard(false, 3,new DiscountEffect(ResourceType.COIN)),
                new LeaderCard(false, 3,new DiscountEffect(ResourceType.COIN)),
                new LeaderCard(false, 3,new DiscountEffect(ResourceType.COIN)),
                new LeaderCard(false, 3,new DiscountEffect(ResourceType.COIN)),
                new LeaderCard(false, 3,new DiscountEffect(ResourceType.COIN)),
                new LeaderCard(false, 3,new DiscountEffect(ResourceType.COIN)),
                new LeaderCard(false, 3,new DiscountEffect(ResourceType.COIN)),
                new LeaderCard(false, 3,new DiscountEffect(ResourceType.COIN)),
                new LeaderCard(false, 3,new DiscountEffect(ResourceType.COIN)),
                new LeaderCard(false, 3,new DiscountEffect(ResourceType.COIN)),

        };

        ArrayList<LeaderCard> defaultDeck = new ArrayList<>(Arrays.asList(defaultDeckArray));
        return new LeaderCardsDeck(defaultDeck);
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


    public List<LeaderCard> getLeaderCards() {
        return leaderCards;
    }

    public void addLeader(LeaderCard leaderCard) {

    }

    public void removeLeaderCard(LeaderCard leaderCard) {

    }
}
