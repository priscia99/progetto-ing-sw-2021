package it.polimi.ingsw.model.turn_manager.action_params;

import it.polimi.ingsw.exceptions.ParamsConvertionException;

import java.util.Map;

public class DropLeaderCardParams {
    final private String cardId;

    public String getCardId(){ return cardId; }

    private DropLeaderCardParams(String cardId){
        this.cardId = cardId;
    }

    static public DropLeaderCardParams fromMap(Map<String, Object> map){
        try{
            String cardId = (String) map.get("cardIndex");
            return new DropLeaderCardParams(cardId);
        } catch (Exception e){
            throw new ParamsConvertionException("Error converting to DropLeaderCardParams");
        }
    }
}
