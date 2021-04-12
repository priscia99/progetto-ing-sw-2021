package it.polimi.ingsw.model.player_board;

import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.observer.Observable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DevelopmentCardsDeck extends Observable<ArrayList<DevelopmentCard>> {

    private final ArrayList<DevelopmentCard> deck;

    public DevelopmentCardsDeck(ArrayList<DevelopmentCard> deck) {
        this.deck = deck;
    }

    public ArrayList<DevelopmentCard> getDeck() {
        return deck;
    }

    public void addCard(DevelopmentCard developmentCard) throws IllegalArgumentException{
        if(getCardNumber() == 3) {
            // This deck contains only up to 3 dev cards
            throw new IllegalArgumentException("This dev cards deck is full");
        }
        if((deck.size() == 0 && developmentCard.getLevel() !=1) || (getTopCard().getLevel() != developmentCard.getLevel()-1)) {
            throw new IllegalArgumentException("The development card level is not valid for this deck");
        }
        deck.add(developmentCard);

        notify(this.deck);
    }

    public int getCardNumber() {
        return deck.size();
    }

    public DevelopmentCard getTopCard() {
        // Top card is located in index size-1 (last array index)
        return deck.get(deck.size()-1);
    }
}
