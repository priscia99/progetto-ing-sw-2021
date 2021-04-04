package it.polimi.ingsw.model.player_board;

import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.card.effect.DiscountEffect;
import it.polimi.ingsw.model.card.effect.EffectType;
import it.polimi.ingsw.model.resource.ResourceType;
import it.polimi.ingsw.utils.CustomLogger;

import java.util.Arrays;
import java.util.List;

public class LeaderCardsDeck {

    private final List<LeaderCard> leaderCards;

    static public LeaderCardsDeck getStartingDeck(){
        final LeaderCard[] defaultDeckArray = {
                //TODO: complete default array deck with correct leader cards data
                new LeaderCard(false, 3,new DiscountEffect(EffectType.DISCOUNT, ResourceType.COIN)),
                new LeaderCard(false, 3,new DiscountEffect(EffectType.DISCOUNT, ResourceType.COIN)),
                new LeaderCard(false, 3,new DiscountEffect(EffectType.DISCOUNT, ResourceType.COIN)),
        };

        final List<LeaderCard> defaultDeck = Arrays.asList(defaultDeckArray);
        return new LeaderCardsDeck(defaultDeck);
    }

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
