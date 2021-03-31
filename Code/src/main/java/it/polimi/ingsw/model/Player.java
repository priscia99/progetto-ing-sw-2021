package it.polimi.ingsw.model;

import java.util.List;

public class Player {
    private String nickname;
    private LeaderCardsDeck leaderCardsDeck;

    public Card pickCard(List<Card> cards){ return null; }

    public void addResource(it.polimi.ingsw.model.Resource resource, int quantity){}

    it.polimi.ingsw.model.TurnAction pickAction(){return null;};
}
