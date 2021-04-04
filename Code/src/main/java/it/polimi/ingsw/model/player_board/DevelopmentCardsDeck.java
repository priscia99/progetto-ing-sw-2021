package it.polimi.ingsw.model.player_board;

import it.polimi.ingsw.model.card.DevelopmentCard;

import java.util.List;

public class DevelopmentCardsDeck {

    private final List<DevelopmentCard> decks;

    public DevelopmentCardsDeck(List<DevelopmentCard> decks) {
        this.decks = decks;
    }

    public List<DevelopmentCard> getDecks() {
        return decks;
    }

    public void addCard(DevelopmentCard developmentCard) {

    }

    public int getCardNumber() {
        return 0;
    }

    public void getTopCard(int slot) {

    }
}
