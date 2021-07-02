package it.polimi.ingsw.client.model;

import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.server.model.card.LeaderCard;
import it.polimi.ingsw.server.model.card.effect.DiscountEffect;
import it.polimi.ingsw.server.model.card.effect.Effect;
import it.polimi.ingsw.server.model.card.effect.EffectType;
import it.polimi.ingsw.utils.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ClientLeaderCardDeck extends Observable<Pair<ClientLeaderCardDeck, String>> implements Serializable {

    private static final long serialVersionUID = 1504L;
    private final String owner;
    private ArrayList<ClientLeaderCard> clientLeaderCards;
    private boolean mine;

    /**
     * Initialize the client leader card deck passing all parameters
     * @param clientLeaderCards the list of player leader cards
     * @param mine set true if the owner is the same as the player that opens this client instance
     * @param owner the name of the leader card deck's owner
     */
    public ClientLeaderCardDeck(ArrayList<ClientLeaderCard> clientLeaderCards, boolean mine, String owner) {
        this.clientLeaderCards = clientLeaderCards;
        this.mine = mine;
        this.owner = owner;
    }

    /**
     * Initialize the client leader card deck
     * @param mine set true if the owner is the same as the player that opens this client instance
     * @param owner the name of the leader card deck's owner
     */
    public ClientLeaderCardDeck(boolean mine, String owner) {
        this.clientLeaderCards = new ArrayList<>();
        this.mine = mine;
        this.owner = owner;
    }

    /**
     * Initialize the client leader card deck by passing all parameters from the server model
     * @param mine set true if the owner is the same as the player that opens this client instance
     * @param owner the name of the leader card deck's owner
     * @param leaderCards list of player leader cards
     */
    public ClientLeaderCardDeck(boolean mine, String owner, ArrayList<LeaderCard> leaderCards){
        this.clientLeaderCards = leaderCards.stream().map(ClientLeaderCard::new).collect(Collectors.toCollection(ArrayList::new));
        this.mine = mine;
        this.owner = owner;
    }

    /**
     * Retrieves the list of player leader cards
     * @return the list of player leader cards
     */
    public ArrayList<ClientLeaderCard> getClientLeaderCards() {
        return clientLeaderCards;
    }

    /**
     * Retrieves a card that is located in a specific position of the list
     * @param index the leader card position in the list
     * @return the selected leader card
     */
    public ClientLeaderCard getCard(int index) {
        return this.clientLeaderCards.get(index);
    }

    /**
     * Retrieves a list of acrive leader cards with the selected effect
     * @param effect effect type
     * @return a list of active leader cards with the selected effect
     */
    public ArrayList<ClientLeaderCard> getActiveCardByEffect(EffectType effect){
        return this.clientLeaderCards.stream().filter(ClientLeaderCard::isActive)
                .filter(card->card.getEffect().getEffectType().equals(effect))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Actvates a leader card with the given ID
     * @param id the id of the leader card which has to be activated
     */
    public void activateById(String id){
        this.clientLeaderCards.stream().filter(c->c.getId().equals(id)).findFirst().get().setActive(true);
        notify(new Pair<>(this, owner));
    }

    /**
     * Sets the client leader cards
     * @param clientLeaderCards list of player leader cards
     * @param displayToView set true if the UI needs to be updated
     */
    public void setClientLeaderCards(ArrayList<ClientLeaderCard> clientLeaderCards, boolean displayToView) {
        this.clientLeaderCards = clientLeaderCards;
        if(displayToView) {
            notify(new Pair<>(this, owner));
        }
    }

    public boolean isMine() { return mine; }

    public void setMine(boolean flag){this.mine = flag;}

    public void show(){ notify(new Pair<>(this, owner)); }

}
