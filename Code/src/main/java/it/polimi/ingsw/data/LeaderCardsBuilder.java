package it.polimi.ingsw.data;

import it.polimi.ingsw.model.card.LeaderCard;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LeaderCardsBuilder{
    ArrayList<LeaderCard> leaderCardList;

    private void initBuilder(){
        // TODO: Code LeaderCardsBuilder init function
    }

    public List<LeaderCard> getDeck(){
        if(Objects.isNull(leaderCardList)) initBuilder();
        return new ArrayList<>(leaderCardList);
    }
}
