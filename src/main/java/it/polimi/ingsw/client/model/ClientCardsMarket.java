package it.polimi.ingsw.client.model;

import it.polimi.ingsw.server.model.card.*;
import it.polimi.ingsw.server.model.market.*;
import java.util.*;

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
