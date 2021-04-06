package it.polimi.ingsw.data;

import it.polimi.ingsw.model.card.DevelopmentCard;

import java.util.ArrayList;
import java.util.List;

import java.util.Objects;

public class DevelopmentCardsBuilder {
    ArrayList<DevelopmentCard> developmentCardList;

    private void initBuilder(){
        // TODO: Code DevelopmentCardsBuilder init function
    }

    public List<DevelopmentCard> getDeck(){
        if(Objects.isNull(developmentCardList)) initBuilder();

        return new ArrayList<>(developmentCardList);
    }
}
