package it.polimi.ingsw_old.view.client_model;

import it.polimi.ingsw_old.model.card.DevelopmentCard;
import it.polimi.ingsw_old.model.market.CardsMarket;

import java.util.Stack;

public class ClientCardsMarket{

    private final Stack<DevelopmentCard>[][] decks;

    public ClientCardsMarket(CardsMarket cardsMarket) {
        this.decks = cardsMarket.getDecks();
    }

    public ClientCardsMarket(Stack<DevelopmentCard>[][] decks) {
        this.decks = decks;
    }

    public Stack<DevelopmentCard>[][] getDecks() {
        return decks;
    }
}
