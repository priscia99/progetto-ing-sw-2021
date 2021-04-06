package it.polimi.ingsw.data;

import com.google.gson.Gson;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderCard;

import java.util.ArrayList;
import java.util.List;

import java.util.Objects;

public class DevelopmentCardsBuilder {
    static private ArrayList<DevelopmentCard> developmentCardList;
    static final private String devCardsPath = "assets/dev_cards.json";

    static private void initBuilder(){
        // TODO: Code DevelopmentCardsBuilder init function
        Gson gson;
    }

    static public List<DevelopmentCard> getDeck(){
        if(Objects.isNull(developmentCardList)) initBuilder();

        return new ArrayList<>(developmentCardList);
    }
}
