package it.polimi.ingsw.client.model;

import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.server.model.card.DevelopmentCard;
import it.polimi.ingsw.server.model.card.effect.ProductionEffect;
import it.polimi.ingsw.server.model.player_board.DevelopmentCardsDeck;
import it.polimi.ingsw.utils.Pair;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ClientDevelopmentCardDecks extends Observable<Pair<ClientDevelopmentCardDecks, String>> implements Serializable {

    private static final long serialVersionUID = 1508L;
    private final ArrayList<ArrayList<ClientDevelopmentCard>> developmentCards;
    private String owner;

    /**
     * Initialize the client development cards decks with default values
     * @param username the name of the deck's owner
     */
    public ClientDevelopmentCardDecks(String username) {
        this.owner = username;
        this.developmentCards = new ArrayList<>();
        this.developmentCards.add(new ArrayList<>());
        this.developmentCards.add(new ArrayList<>());
        this.developmentCards.add(new ArrayList<>());
    }

    /**
     * Initialize the client development cards decks by passing all parameters
     * @param developmentCards list of development card decks
     * @param username the name of the deck's owner
     */
    public ClientDevelopmentCardDecks(DevelopmentCardsDeck[] developmentCards, String username) {
        ArrayList<ArrayList<ClientDevelopmentCard>> clientCards = new ArrayList<>();
        for (DevelopmentCardsDeck list : developmentCards) {
            ArrayList<ClientDevelopmentCard> listToAdd = new ArrayList<>();
            for (DevelopmentCard card : list.getDeck()) {
                listToAdd.add(new ClientDevelopmentCard(card));
            }
            clientCards.add(listToAdd);
        }
        this.developmentCards = clientCards;
        this.owner = username;
    }

    /**
     *
     * @return all player's development cards
     */
    public ArrayList<ArrayList<ClientDevelopmentCard>> getDevelopmentCards() {
        return developmentCards;
    }


    /**
     * Retrieves the selected development card deck
     * @param index index of the deck
     * @return the selected development card deck
     */
    public ArrayList<ClientDevelopmentCard> getDeck(int index){return this.developmentCards.get(index);}

    public ArrayList<ProductionEffect> getProductionAvailable(ArrayList<String> ids){
        return new ArrayList<>(this.developmentCards.stream()
                .filter(deck -> deck.size()>0)
                .filter(deck->ids.contains(deck.get(deck.size()-1).getId()))
                .map(deck->(ProductionEffect) deck.get(deck.size()-1).getEffect()).collect(Collectors.toList()));
    }

    /**
     * Add a development card to the selected deck
     * @param card development card to add
     * @param index index of the deck in which the card needs to be placed
     */
    public void addCard(ClientDevelopmentCard card, int index) {
        this.developmentCards.get(index).add(card);

        notify(new Pair<>(this, owner));
    }

    /**
     * Retrieves the number of development cards stored in the decks
     * @return the number of development cards stored in the decks
     */
    public int getCardsNumber(){
        int cardsNumber = 0;
        for(ArrayList<ClientDevelopmentCard> cardsDeck: developmentCards){
            cardsNumber += cardsDeck.size();
        }
        return cardsNumber;
    }

    /**
     * Notify observers to trigger display of the deck
     */
    public void show(){
        notify(new Pair<>(this, owner));
    }

    public boolean isInitialized(){
        return developmentCards.size() != 0;
    }
}
