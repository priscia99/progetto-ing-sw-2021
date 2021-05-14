package it.polimi.ingsw.model.player_board;

import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.network.MessageType;
import it.polimi.ingsw.network.observer.ObservableWithOption;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class that models a deck of development cards.
 */
public class DevelopmentCardsDeck extends ObservableWithOption<Object, MessageType> implements Serializable {

    private static final long serialVersionUID = 1L;    // for serialization

    private final ArrayList<DevelopmentCard> deck;

    /**
     * Create a DevelopmentCardsDeck object with a given list of cards.
     * @param developmentCards the initial list of development cards in the deck
     */
    public DevelopmentCardsDeck(ArrayList<DevelopmentCard> developmentCards) {
        this.deck = developmentCards;
    }

    /**
     *
     * @return the list of development cards in the deck.
     */
    public ArrayList<DevelopmentCard> getDeck() {
        return deck;
    }

    /**
     * Add a new development card to the deck.
     * @param developmentCard the leader card to add.
     * @throws IllegalArgumentException if deck is already full, or if the deck cannot accept a card of
     * the level of the one give
     */
    public void addCard(DevelopmentCard developmentCard) throws IllegalArgumentException{
        if(this.isFull()) {
            throw new IllegalArgumentException("This dev cards deck is full");
        }
        if((deck.size() == 0 && developmentCard.getLevel() != 1) || (deck.size() != 0 && getTopCard().getLevel() != developmentCard.getLevel()-1)) {
            throw new IllegalArgumentException("The development card level is not valid for this deck");
        }
        deck.add(developmentCard);

        notify(this, MessageType.BUY_DEVELOPMENT_CARD);
    }

    /**
     * Test if the deck is full.
     * @return true if the deck is full, false if not.
     */
    public boolean isFull() {
        return getCardNumber() == 3;
    }

    /**
     *
     * @return the number of cards in the deck.
     */
    public int getCardNumber() {
        return deck.size();
    }

    /**
     *
     * @return the top card of the deck without removing it.
     */
    public DevelopmentCard getTopCard() {
        // Top card is located in index size-1 (last array index)
        return deck.get(deck.size()-1);
    }
}
