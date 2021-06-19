package it.polimi.ingsw.client.model;

import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.server.model.card.effect.DiscountEffect;
import it.polimi.ingsw.server.model.card.effect.Effect;
import it.polimi.ingsw.server.model.card.effect.EffectType;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ClientLeaderCardDeck extends Observable<ClientLeaderCardDeck> {

    private ArrayList<ClientLeaderCard> clientLeaderCards;
    private final boolean mine;

    public ClientLeaderCardDeck(ArrayList<ClientLeaderCard> clientLeaderCards, boolean mine) {
        this.clientLeaderCards = clientLeaderCards;
        this.mine = mine;
    }

    public ClientLeaderCardDeck(boolean mine) {
        this.clientLeaderCards = new ArrayList<>();
        this.mine = mine;
    }

    public ArrayList<ClientLeaderCard> getClientLeaderCards() {
        return clientLeaderCards;
    }

    public ClientLeaderCard getCard(int index) {
        return this.clientLeaderCards.get(index);
    }

    public <T> ArrayList<T> getActiveEffects(EffectType effect){
        return this.clientLeaderCards.stream().filter(ClientLeaderCard::isActive)
                .filter(card->card.getEffect().getEffectType().equals(effect))
                .map(card->(T) card.getEffect())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void activateById(String id){
        this.clientLeaderCards.stream().filter(c->c.getId().equals(id)).findFirst().get().setActive(true);
        notify(this);
    }

    public void addCard(ClientLeaderCard card) {
        this.clientLeaderCards.add(card);

        notify(this);
    }

    public void addCards(ArrayList<ClientLeaderCard> cards) {
        this.clientLeaderCards.addAll(cards);

        notify(this);
    }

    public void removeCard(int index) {
        this.clientLeaderCards.remove(this.clientLeaderCards.get(index));

        notify(this);
    }

    public void removeCards(int... indexes) {
        for (int i = 0; i < indexes.length; i++) {
            this.clientLeaderCards.remove(this.clientLeaderCards.get(i));
        }

        notify(this);
    }

    public boolean isMine() {
        return mine;
    }

    public void setClientLeaderCards(ArrayList<ClientLeaderCard> clientLeaderCards, boolean displayToView) {
        this.clientLeaderCards = clientLeaderCards;
        if(displayToView) {
            notify(this);
        }
    }

    public void show(){
        notify(this);
    }
}
