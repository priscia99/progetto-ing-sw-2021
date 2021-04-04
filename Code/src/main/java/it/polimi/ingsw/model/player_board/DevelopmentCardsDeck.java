package it.polimi.ingsw.model.player_board;

import it.polimi.ingsw.model.card.DevelopmentCard;

import java.util.List;

public class DevelopmentCardsDeck {

    private final List<DevelopmentCard> deck;

    public DevelopmentCardsDeck(List<DevelopmentCard> deck) {
        this.deck = deck;
    }

    public List<DevelopmentCard> getDeck() {
        return deck;
    }

    public void addCard(DevelopmentCard developmentCard) {

    }

    public int getCardNumber() {
        return 0;
    }

    public void getTopCard(int slot) {

    }
}
