package it.polimi.ingsw.model;

import java.util.Stack;

public class CardMarket {

    private final Stack<DevelopmentCard>[][] decks;

    public CardMarket(Stack<DevelopmentCard>[][] decks) {
        this.decks = decks;
    }

    public Stack<DevelopmentCard>[][] getDecks() {
        return decks;
    }

    public void removeCard(DevelopmentCard toRemove) {}
}
