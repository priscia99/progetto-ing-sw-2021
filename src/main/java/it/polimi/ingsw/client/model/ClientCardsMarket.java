package it.polimi.ingsw.client.model;

import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.server.model.card.*;
import it.polimi.ingsw.server.model.market.*;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class ClientCardsMarket extends Observable<ClientCardsMarket> implements Serializable {
    private ArrayList<ArrayList<ArrayList<ClientDevelopmentCard>>> decks = new ArrayList<>();

    public ClientCardsMarket() {}

    /**
     * Initialize the client development cards market by passing the card market from the server model
     * @param cardsMarket client development cards market
     */
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

    /**
     * Set all decks of the development cards market
     * @param decks
     */
    public void setDecks(ArrayList<ArrayList<ArrayList<ClientDevelopmentCard>>> decks) {
        this.decks = decks;
        notify(this);
    }

    /**
     * Retrieves a card from the development cards market with the requested id
     * @param id id of the card
     * @return a card from the development cards market with the requested id
     */
    public ClientDevelopmentCard getCardById(String id){
        for(int i = 0; i<4; i++){
            for(int j = 0; j<3; j++){
                if(!this.decks.get(i).get(j).isEmpty()){
                    int index = this.decks.get(i).get(j).size() - 1;
                    if(this.decks.get(i).get(j).get(index).getId().equals(id)) return this.decks.get(i).get(j).get(index);
                }
            }
        }
        return null;
    }

    /**
     *
     * @return all decks from the development cards market
     */
    public ArrayList<ArrayList<ArrayList<ClientDevelopmentCard>>> getDecks() {
        return decks;
    }
}
