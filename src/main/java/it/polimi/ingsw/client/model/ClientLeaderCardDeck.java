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

    private final String owner;
    private ArrayList<ClientLeaderCard> clientLeaderCards;
    private boolean mine;

    public ClientLeaderCardDeck(ArrayList<ClientLeaderCard> clientLeaderCards, boolean mine, String owner) {
        this.clientLeaderCards = clientLeaderCards;
        this.mine = mine;
        this.owner = owner;
    }

    public ClientLeaderCardDeck(boolean mine, String owner) {
        this.clientLeaderCards = new ArrayList<>();
        this.mine = mine;
        this.owner = owner;
    }

    public ClientLeaderCardDeck(boolean mine, String owner, ArrayList<LeaderCard> leaderCards){
        this.clientLeaderCards = leaderCards.stream().map(ClientLeaderCard::new).collect(Collectors.toCollection(ArrayList::new));
        this.mine = mine;
        this.owner = owner;
    }

    public ArrayList<ClientLeaderCard> getClientLeaderCards() {
        return clientLeaderCards;
    }

    public ClientLeaderCard getCard(int index) {
        return this.clientLeaderCards.get(index);
    }

    public ArrayList<ClientLeaderCard> getActiveCardByEffect(EffectType effect){
        return this.clientLeaderCards.stream().filter(ClientLeaderCard::isActive)
                .filter(card->card.getEffect().getEffectType().equals(effect))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void activateById(String id){
        this.clientLeaderCards.stream().filter(c->c.getId().equals(id)).findFirst().get().setActive(true);
        notify(new Pair<>(this, owner));
    }

    public void addCard(ClientLeaderCard card) {
        this.clientLeaderCards.add(card);

        notify(new Pair<>(this, owner));
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean flag){this.mine = flag;}

    public void setClientLeaderCards(ArrayList<ClientLeaderCard> clientLeaderCards, boolean displayToView) {
        this.clientLeaderCards = clientLeaderCards;
        if(displayToView) {
            notify(new Pair<>(this, owner));
        }
    }

    public void show(){
        notify(new Pair<>(this, owner));
    }

}
