package it.polimi.ingsw.client.model;

import it.polimi.ingsw.server.model.card.*;
import it.polimi.ingsw.server.model.market.*;
import java.util.*;

public class ClientCardsMarket{
    private Stack<DevelopmentCard>[][] decks = new Stack[0][];

    public ClientCardsMarket() {}

    public ClientCardsMarket(Stack<DevelopmentCard>[][] decks) {
        this.decks = decks;
    }

    public void setDecks(Stack<DevelopmentCard>[][] decks) {
        this.decks = decks;
    }

    public Stack<DevelopmentCard>[][] getDecks() {
        return decks;
    }
}
