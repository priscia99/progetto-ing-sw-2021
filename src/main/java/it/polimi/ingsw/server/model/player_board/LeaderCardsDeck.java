package it.polimi.ingsw.server.model.player_board;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.data.LeaderCardsBuilder;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.server.model.card.LeaderCard;
import it.polimi.ingsw.server.model.card.effect.EffectType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Class that models a list of leader cards.
 */
public class LeaderCardsDeck extends Observable<Message<ClientController>> {

    private final ArrayList<LeaderCard> leaderCards;

    /**
     * Call the builder to create the starting leader cards deck already shuffled.
     * @return the initial shuffled leader cards deck.
     */
    static public LeaderCardsDeck getStartingDeck(){
        return new LeaderCardsDeck(LeaderCardsBuilder.getDeck());
    }

    /**
     * Create a deck of leader cards deck from a given list of leader cards.
     * @param leaderCards the list of leader cards initially contained in the deck
     */
    public LeaderCardsDeck(ArrayList<LeaderCard> leaderCards) {
        this.leaderCards = leaderCards;
    }

    /**
     * Create an empty deck of leader cards.
     */
    public LeaderCardsDeck(){
        this.leaderCards = new ArrayList<>();
    }

    /**
     * Shuffle cards in the deck.
     */
    public void shuffle(){
        Collections.shuffle(this.leaderCards);
    }

    /**
     * Extract (and remove) the card at the top of the deck.
     * @return the top leader card.
     */
    public LeaderCard pop() throws Exception {
        if(leaderCards.isEmpty()) throw new Exception("This player's leader cards deck is empty.");
        return this.leaderCards.remove(this.leaderCards.size()-1);
    }

    public LeaderCardsDeck getCopy(){
        LeaderCardsDeck copy = new LeaderCardsDeck();
        copy.addLeaders(this.leaderCards);
        return copy;
    }

    /**
     *
     * @return the list of leader cards in the deck.
     */
    public ArrayList<LeaderCard> getLeaderCards() {
        return leaderCards;
    }

    /**
     * Add a new leader card to the deck.
     * @param leaderCard the new leader card to add.
     */
    public void addLeader(LeaderCard leaderCard) {
        leaderCards.add(leaderCard);
    }

    public void addLeaders(ArrayList<LeaderCard> leaderCards) {
        this.leaderCards.addAll(leaderCards);
    }

    /**
     * Activate a card identified by its id.
     * @param cardId the id of the card to activate.
     */
    public void activateLeaderCardById(String cardId){
        leaderCards.stream().filter(card-> card.getId().equals(cardId)).findFirst().get().play();
    }

    /**
     * Remove a card identified by its id.
     * @param cardId the id of the card to remove.
     */
    public void removeLeaderCardById(String cardId) throws Exception {
        if(leaderCards.isEmpty()) {
            throw new Exception("This player's leader cards deck is empty.");
        }
        else if (!leaderCards.removeIf(card-> card.getId().equals(cardId))) {
            throw new IllegalArgumentException("LeaderCard not present this player's deck");
        }
    }

    public <T> ArrayList<T> getActiveEffects(EffectType effect){
        return this.leaderCards.stream().filter(LeaderCard::isActive)
                .filter(card->card.getEffect().getEffectType().equals(effect))
                .map(card->(T) card.getEffect())
                .collect(Collectors.toCollection(ArrayList::new));
    }


}
