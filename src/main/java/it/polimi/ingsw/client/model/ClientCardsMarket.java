package it.polimi.ingsw.client.model;

import it.polimi.ingsw.server.model.card.*;
import it.polimi.ingsw.server.model.market.*;
import java.util.*;
import java.util.stream.Collectors;

public class ClientCardsMarket{
    private ArrayList<ArrayList<ArrayList<ClientDevelopmentCard>>> decks = new ArrayList<>();

    public ClientCardsMarket() {}

    public ClientCardsMarket(CardsMarket cardsMarket) {
        Stack<DevelopmentCard>[][] decks = cardsMarket.getDecks();
        ArrayList<ArrayList<ArrayList<ClientDevelopmentCard>>> tempDecks = new ArrayList<>();
        for (Stack<DevelopmentCard>[] deck : decks) {
            ArrayList<ArrayList<ClientDevelopmentCard>> tempDeck = new ArrayList<>();
            for (Stack<DevelopmentCard> developmentCards : deck) {
                ArrayList<ClientDevelopmentCard> tempStack = (new ArrayList<>(developmentCards))
                        .stream()
                        .map(ClientDevelopmentCard::new)
                        .collect(Collectors.toCollection(ArrayList::new));
                tempDeck.add(tempStack);
            }
            tempDecks.add(tempDeck);
        }
        this.decks = tempDecks;
    }

    public ClientCardsMarket(Stack<DevelopmentCard>[][] decks) {
        ArrayList<ArrayList<ArrayList<ClientDevelopmentCard>>> tempDecks = new ArrayList<>();
        for (Stack<DevelopmentCard>[] deck : decks) {
            ArrayList<ArrayList<ClientDevelopmentCard>> tempDeck = new ArrayList<>();
            for (Stack<DevelopmentCard> developmentCards : deck) {
                ArrayList<ClientDevelopmentCard> tempStack = (new ArrayList<>(developmentCards))
                        .stream()
                        .map(ClientDevelopmentCard::new)
                        .collect(Collectors.toCollection(ArrayList::new));
                tempDeck.add(tempStack);
            }
            tempDecks.add(tempDeck);
        }
        this.decks = tempDecks;
    }

    public void setDecks(ArrayList<ArrayList<ArrayList<ClientDevelopmentCard>>> decks) {
        this.decks = decks;
    }

    public ArrayList<ArrayList<ArrayList<ClientDevelopmentCard>>> getDecks() {
        return decks;
    }
}
