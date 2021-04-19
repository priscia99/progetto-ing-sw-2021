package it.polimi.ingsw.model.turn_manager.action_params;

import it.polimi.ingsw.exceptions.ParamsConvertionException;
import it.polimi.ingsw.model.marble.Orientation;

import java.util.ArrayList;
import java.util.Map;

public class PlayLeaderCardParams {
    final String cardId;
    private PlayLeaderCardParams(String id){
        this.cardId = id;
    }

    public String getCardId(){return cardId;}

    static public PlayLeaderCardParams fromMap(Map<String, Object> map){
        try{
            String cardId = (String) map.get("cardId");
            return new PlayLeaderCardParams(cardId);
        } catch (Exception e){
            throw new ParamsConvertionException("Error while converting play leader card params");
        }
    }
}
