package it.polimi.ingsw.client.model;

import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.server.model.card.effect.ProductionEffect;
import it.polimi.ingsw.utils.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ClientDevelopmentCardDecks extends Observable<Pair<ClientDevelopmentCardDecks, String>> {

    private final ArrayList<ArrayList<ClientDevelopmentCard>> developmentCards;
    private String owner;

    public ClientDevelopmentCardDecks(ArrayList<ArrayList<ClientDevelopmentCard>> developmentCards, String username) {
        this.developmentCards = developmentCards;
    }

    public ClientDevelopmentCardDecks(String username) {
        this.developmentCards = new ArrayList<>();
    }

    public ArrayList<ArrayList<ClientDevelopmentCard>> getDevelopmentCards() {
        return developmentCards;
    }

    public ClientDevelopmentCard getCard(Pair<Integer, Integer> coordinates) {
        return this.developmentCards.get(coordinates.getFirst()).get(coordinates.getSecond());
    }

    public ArrayList<ClientDevelopmentCard> getDeck(int index){return this.developmentCards.get(index);}

    public ArrayList<ProductionEffect> getProductionAvailable(ArrayList<String> ids){
        return new ArrayList<>(this.developmentCards.stream()
                .filter(deck->ids.contains(deck.get(0).getId()))
                .map(deck->(ProductionEffect) deck.get(0).getEffect()).collect(Collectors.toList()));
    }


    public void addCard(ClientDevelopmentCard card, int index) {
        this.developmentCards.get(index).add(card);

        notify(new Pair<>(this, owner));
    }

    public void addCards(ArrayList<ClientDevelopmentCard> cards, int index) {
        this.developmentCards.get(index).addAll(cards);

        notify(new Pair<>(this, owner));
    }

    public void removeCard(Pair<Integer, Integer> coordinates) {
        ClientDevelopmentCard cardToRemove = this.developmentCards.get(coordinates.getFirst()).get(coordinates.getSecond());
        this.developmentCards.get(coordinates.getFirst()).remove(cardToRemove);

        notify(new Pair<>(this, owner));
    }

    public void show(){
        notify(new Pair<>(this, owner));
    }
    public int getCardsNumber(){return developmentCards.size();}
    public boolean isInitialized(){
        return developmentCards.size() != 0;
    }
}
