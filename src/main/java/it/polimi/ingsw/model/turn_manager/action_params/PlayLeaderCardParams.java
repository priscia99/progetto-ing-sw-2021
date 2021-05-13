package it.polimi.ingsw.model.turn_manager.action_params;

import it.polimi.ingsw.exceptions.ParamsConvertionException;
import it.polimi.ingsw.model.marble.Orientation;

import java.util.ArrayList;
import java.util.Map;

public class PlayLeaderCardParams extends ActionParams{
    final String cardId;
    public PlayLeaderCardParams(String id){
        this.cardId = id;
    }

    public String getCardId(){return cardId;}


}
