package it.polimi.ingsw.model.turn_manager.action_params;

import it.polimi.ingsw.exceptions.ParamsConvertionException;

import java.util.Map;

public class DropLeaderCardParams extends ActionParams {
    final private String cardId;

    public String getCardId(){ return cardId; }

    public DropLeaderCardParams(String cardId){
        this.cardId = cardId;
    }

}
