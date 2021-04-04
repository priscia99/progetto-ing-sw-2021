package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.color.Color;
import it.polimi.ingsw.model.card.effect.EffectType;
import it.polimi.ingsw.model.card.effect.ProductionEffect;
import it.polimi.ingsw.model.resource.ResourcePile;

import java.util.List;
import java.util.Stack;

public class CardMarket {

    private final Stack<DevelopmentCard>[][] decks;

    static public CardMarket getStartingMarket(){
        final Stack<DevelopmentCard> levelOneBlueCards = new Stack<>();
        //TODO: complete all development cards stacks
        levelOneBlueCards.push(new DevelopmentCard(false, 3, 1, new ProductionEffect(EffectType.PRODUCTION, null, null), Color.BLUE));

        final Stack<DevelopmentCard>[][] initialDecks = (Stack<DevelopmentCard>[][]) new Stack[1][1];
        initialDecks[0][0] = levelOneBlueCards;

        return new CardMarket(initialDecks);
    }

    public CardMarket(Stack<DevelopmentCard>[][] decks) {
        this.decks = decks;
    }

    public Stack<DevelopmentCard>[][] getDecks() {
        return decks;
    }

    public void removeCard(DevelopmentCard toRemove) {

    }
}
