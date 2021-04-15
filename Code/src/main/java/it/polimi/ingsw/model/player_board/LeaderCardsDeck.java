package it.polimi.ingsw.model.player_board;

import it.polimi.ingsw.data.LeaderCardsBuilder;
import it.polimi.ingsw.exceptions.EmptyDeckException;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.observer.Observable;

import java.util.*;

public class LeaderCardsDeck extends Observable<ArrayList<LeaderCard>> {

    private final ArrayList<LeaderCard> leaderCards;

    static public LeaderCardsDeck getStartingDeck(){
        return new LeaderCardsDeck(LeaderCardsBuilder.getDeck());
    }

    public LeaderCardsDeck(ArrayList<LeaderCard> leaderCards) {
        this.leaderCards = leaderCards;
    }

    public LeaderCardsDeck(){
        this.leaderCards = new ArrayList<>();
    }

    public void shuffle(){
        Collections.shuffle(this.leaderCards);
    }

    public LeaderCard pop(){
        if(leaderCards.isEmpty()) throw new EmptyDeckException("This player's leader cards deck is empty.");
        return this.leaderCards.remove(this.leaderCards.size()-1);
    }


    public ArrayList<LeaderCard> getLeaderCards() {
        return leaderCards;
    }

    public void addLeader(LeaderCard leaderCard) {
        leaderCards.add(leaderCard);
        notify(this.leaderCards);
    }

    public void clear(){
        leaderCards.clear();
    }

    public void activateWithId(String cardId){
        leaderCards.stream().filter(card->card.getId() == cardId).findFirst().get().play();
    }

    public void removeLeaderCardWithId(String cardId) {
        if(leaderCards.isEmpty()) {
            throw new EmptyDeckException("This player's leader cards deck is empty.");
        }
        else if (!leaderCards.removeIf(card->card.getId()==cardId)) {
            throw new IllegalArgumentException("LeaderCard not present this player's deck");
        }
        notify(this.leaderCards);
    }
}
