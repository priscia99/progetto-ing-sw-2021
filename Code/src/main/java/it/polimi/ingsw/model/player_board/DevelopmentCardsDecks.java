package it.polimi.ingsw.model.player_board;

import it.polimi.ingsw.model.card.DevelopmentCard;

import java.util.Stack;

public class DevelopmentCardsDecks {

    private final Stack<DevelopmentCard>[] decks;

    public DevelopmentCardsDecks(Stack<DevelopmentCard>[] decks) {
        this.decks = decks;
    }

    public Stack<DevelopmentCard>[] getDecks() {
        return decks;
    }

    public void addCard(DevelopmentCard developmentCard) {

    }

    public int getCardNumber() {
        return 0;
    }

    public void getTopCard(int slot) {

    }
}
