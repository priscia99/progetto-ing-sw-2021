package it.polimi.ingsw.model.player_board;

import it.polimi.ingsw.model.card.DevelopmentCard;

import java.util.ArrayList;
import java.util.List;

public class DevelopmentCardsDeck {

    private final ArrayList<DevelopmentCard> deck;

    public DevelopmentCardsDeck(ArrayList<DevelopmentCard> deck) {
        this.deck = deck;
    }

    public ArrayList<DevelopmentCard> getDeck() {
        return deck;
    }

    public void addCard(DevelopmentCard developmentCard) {

    }

    public int getCardNumber() {
        // TODO: check getCardNumber();
        return deck.size();
    }

    public DevelopmentCard getTopCard(int slot) {
        // TODO: check getTopCard();
        return deck.get(0);
    }
}
